package ru.dankos.yahoostockapi.service

import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.client.NasdaqClient
import ru.dankos.yahoostockapi.client.dto.NasdaqMarketPriceResponse
import ru.dankos.yahoostockapi.controller.dto.DividendInfoResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException
import ru.dankos.yahoostockapi.model.DividendInfo
import ru.dankos.yahoostockapi.model.HistoryPrice
import ru.dankos.yahoostockapi.model.ReturnsCapital
import ru.dankos.yahoostockapi.model.StockMarketInfo
import ru.dankos.yahoostockapi.utils.roundTo
import ru.dankos.yahoostockapi.utils.toPrettyString
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class StockAnalysisService(
    private val cacheableStockService: CacheableStockService,
    private val yahooMarketDataInfoService: YahooMarketDataInfoService,
    private val nasdaqClient: NasdaqClient,
) {

    suspend fun getReturnsCapital(ticker: String): ReturnsCapital = try {
        cacheableStockService.getReturnsCapital(ticker)
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }

    suspend fun getHistoricalPrice(ticker: String): NasdaqMarketPriceResponse = try {
        val date = LocalDate.of(1994, 3, 2)
        val stringDate = date.toString()
        nasdaqClient.getHistoricalPriceByTicker(ticker, stringDate, date.toString()).awaitSingle()
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }

    suspend fun getStockHistoricalDividendsByTicker(ticker: String): List<DividendInfoResponse> = coroutineScope {
        try {
            val stockDividendInfoByTicker = cacheableStockService.getStockDividendInfoByTicker(ticker)

            stockDividendInfoByTicker
                .filter {
                    it.exDate?.isBefore(LocalDate.now()) ?: false && it.exDate?.isAfter(LocalDate.of(1990,1,1)
                    ) ?: false
                }
                .map {
                    async {
                        mapToDividendInfoResponse(
                            it,
                            cacheableStockService.getHistoryPriceByTicker(
                                ticker,
                                it.exDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                it.exDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            )
                        )
                    }
                }.awaitAll()

        } catch (e: Exception) {
            if (e is FeignException && e.status() == 404) {
                throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
            }
            throw InternalException(e)
        }
    }

    suspend fun getStockFutureDividendsByTicker(ticker: String): List<DividendInfoResponse> = coroutineScope {
        try {
            val stockMarketInfo = async { yahooMarketDataInfoService.getStockMarketInfoByTicker(ticker) }
            val stockDividendInfoByTicker = async { cacheableStockService.getStockDividendInfoByTicker(ticker) }
            stockDividendInfoByTicker.await()
                .filter { it.exDate?.isAfter(LocalDate.now()) ?: false }
                .map { mapToDividendInfoResponse(it, stockMarketInfo.await()) }
                .toList()
        } catch (e: Exception) {
            if (e is FeignException && e.status() == 404) {
                throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
            }
            throw InternalException(e)
        }
    }

    suspend fun getStockFutureDividendsByTickers(request: TickersListRequest): List<List<DividendInfoResponse>> =
        coroutineScope {
            request.tickers.map { async { getStockFutureDividendsByTicker(it) } }.awaitAll()
        }

    suspend fun getStockHistoricalDividendsByTickers(request: TickersListRequest): List<List<DividendInfoResponse>> =
        coroutineScope {
            request.tickers.map { async { getStockHistoricalDividendsByTicker(it) } }.awaitAll()
        }

    private fun mapToDividendInfoResponse(
        dividendInfo: DividendInfo,
        historyPrice: HistoryPrice,
    ): DividendInfoResponse {
        val dividendIncome = calculateDividendIncome(dividendInfo, historyPrice)
        val prettyDividendIncome = convertToPrettyDividendIncome(dividendIncome)
        return DividendInfoResponse(
            exDate = dividendInfo.exDate?.toString() ?: "N/A",
            amount = dividendInfo.amount,
            recordDate = dividendInfo.recordDate?.toString() ?: "N/A",
            paymentDate = dividendInfo.paymentDate?.toString() ?: "N/A",
            prettyDividendIncome = prettyDividendIncome,
            dividendIncome = dividendIncome,
            prettyAmount = dividendInfo.amount.toPrettyString()
        )
    }

    private fun mapToDividendInfoResponse(
        dividendInfo: DividendInfo,
        stockMarketInfo: StockMarketInfo,
    ): DividendInfoResponse {
        val dividendIncome = calculateDividendIncome(dividendInfo, stockMarketInfo)
        val prettyDividendIncome = convertToPrettyDividendIncome(dividendIncome)
        return DividendInfoResponse(
            exDate = dividendInfo.exDate?.toString() ?: "N/A",
            amount = dividendInfo.amount,
            recordDate = dividendInfo.recordDate?.toString() ?: "N/A",
            paymentDate = dividendInfo.paymentDate?.toString() ?: "N/A",
            prettyDividendIncome = prettyDividendIncome,
            dividendIncome = dividendIncome,
            prettyAmount = dividendInfo.amount.toPrettyString()
        )
    }

    private fun convertToPrettyDividendIncome(calculatedDividendIncome: Double?): String =
        if (calculatedDividendIncome == null) "N/A" else "$calculatedDividendIncome%"

    private fun calculateDividendIncome(
        dividendInfo: DividendInfo,
        historyPrice: HistoryPrice
    ): Double? {
        var dividendValue = dividendInfo.amount.value
        var priceValue = historyPrice.value.value
        if (dividendValue == null) {
            return null
        } else {
            var dividendMinorUnits = dividendInfo.amount.minorUnits
            var priceMinorUnits = historyPrice.value.minorUnits
            if (dividendMinorUnits != priceMinorUnits) {
                            while (dividendMinorUnits != 1000000) {
                                dividendMinorUnits = dividendMinorUnits?.times(10)
                                dividendValue = dividendValue?.times(10)
                            }
                            while (priceMinorUnits != 1000000) {
                                priceMinorUnits= priceMinorUnits?.times(10)
                                priceValue = priceValue?.times(10)
                            }
                        }

            return ((dividendValue!!.toDouble() / priceValue!!.toDouble()) * 100).roundTo(2)
        }
    }


    private fun calculateDividendIncome(
        dividendInfo: DividendInfo,
        stockMarketInfo: StockMarketInfo,
    ): Double? =
        if (dividendInfo.amount.value == null || stockMarketInfo.marketPrice.value == null) {
            null
        } else {
            ((dividendInfo.amount.value.toDouble() / stockMarketInfo.marketPrice.value) * 100).roundTo(2)
        }
}