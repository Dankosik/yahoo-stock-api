package ru.dankos.yahoostockapi.service

import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.DividendInfoResponse
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException
import ru.dankos.yahoostockapi.model.DividendInfo
import ru.dankos.yahoostockapi.model.ReturnsCapital
import ru.dankos.yahoostockapi.model.StockMarketInfo
import ru.dankos.yahoostockapi.utils.roundTo
import ru.dankos.yahoostockapi.utils.toPrettyString
import java.time.LocalDate

@Service
class StockAnalysisService(
    private val cacheableStockService: CacheableStockService,
    private val yahooMarketDataInfoService: YahooMarketDataInfoService
) {

    suspend fun getReturnsCapital(ticker: String): ReturnsCapital = try {
        cacheableStockService.getReturnsCapital(ticker)
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }

    suspend fun getStockHistoricalDividendsByTicker(ticker: String): List<DividendInfoResponse> = coroutineScope {
        try {
            val stockMarketInfo = async { yahooMarketDataInfoService.getStockMarketInfoByTicker(ticker) }
            val stockDividendInfoByTicker = async { cacheableStockService.getStockDividendInfoByTicker(ticker) }
            stockDividendInfoByTicker.await()
                .filter { it.exDate.isBefore(LocalDate.now()) }
                .map { mapToDividendInfoResponse(it, stockMarketInfo.await()) }
                .toList()
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
                .filter { it.exDate.isAfter(LocalDate.now()) }
                .map { mapToDividendInfoResponse(it, stockMarketInfo.await()) }
                .toList()
        } catch (e: Exception) {
            if (e is FeignException && e.status() == 404) {
                throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
            }
            throw InternalException(e)
        }
    }

    private fun mapToDividendInfoResponse(
        dividendInfo: DividendInfo,
        stockMarketInfo: StockMarketInfo
    ): DividendInfoResponse {
        val dividendIncome = calculateDividendIncome(dividendInfo, stockMarketInfo)
        val prettyDividendIncome = convertToPrettyDividendIncome(dividendIncome)
        return DividendInfoResponse(
            exDate = dividendInfo.exDate.toString(),
            amount = dividendInfo.amount,
            recordDate = dividendInfo.recordDate.toString(),
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
        stockMarketInfo: StockMarketInfo
    ): Double? = if (dividendInfo.amount.value == null || stockMarketInfo.marketPrice.value == null) {
        null
    } else {
        ((dividendInfo.amount.value.toDouble() / stockMarketInfo.marketPrice.value) * 100).roundTo(2)
    }
}