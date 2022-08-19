package ru.dankos.yahoostockapi.service

import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import mu.KLogging
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.converter.toStockPriceResponse
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException

@Service
class YahooPriceService(
    private val cacheableStockService: CacheableStockService,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse = try {
        cacheableStockService.getStockMarketInfoByTicker(ticker).toStockPriceResponse()
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }

    suspend fun getStocksByTickers(request: TickersListRequest): List<StockPriceResponse> = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }

    companion object : KLogging()
}