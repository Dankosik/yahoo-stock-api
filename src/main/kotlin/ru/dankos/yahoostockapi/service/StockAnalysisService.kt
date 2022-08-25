package ru.dankos.yahoostockapi.service

import feign.FeignException
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.DividendInfoResponse
import ru.dankos.yahoostockapi.converter.toDividendInfoResponse
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException
import ru.dankos.yahoostockapi.model.ReturnsCapital
import java.time.LocalDate

@Service
class StockAnalysisService(
    private val cacheableStockService: CacheableStockService,
    private val yahooPriceService: YahooPriceService
) {

    suspend fun getReturnsCapital(ticker: String): ReturnsCapital = cacheableStockService.getReturnsCapital(ticker)

    suspend fun getStockHistoricalDividendsByTicker(ticker: String): List<DividendInfoResponse> = try {
        val stockPriceByTicker = yahooPriceService.getStockPriceByTicker(ticker)
        val stockDividendInfoByTicker = cacheableStockService.getStockDividendInfoByTicker(ticker)
        stockDividendInfoByTicker
            .filter { it.exDate.isBefore(LocalDate.now()) }
            .map { it.toDividendInfoResponse() }
            .toList()
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }

    suspend fun getStockFutureDividendsByTicker(ticker: String): List<DividendInfoResponse> = try {
        cacheableStockService.getStockDividendInfoByTicker(ticker)
            .filter { it.exDate.isAfter(LocalDate.now()) }
            .map { it.toDividendInfoResponse() }
            .toList()
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }
}