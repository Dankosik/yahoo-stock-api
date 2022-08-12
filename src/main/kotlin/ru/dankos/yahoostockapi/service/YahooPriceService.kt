package ru.dankos.yahoostockapi.service

import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.converter.toStockPriceResponse
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class YahooPriceService(
    private val cacheStockService: CacheStockService
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse = try {
        cacheStockService.getStockMarketInfoByTicker(ticker).awaitSingle().toStockPriceResponse()
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found")
        }
        throw InternalException(e)
    }

    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo =
        cacheStockService.getStockMarketInfoByTicker(ticker).awaitSingle()

    suspend fun getStocksByTickers(request: TickersListRequest): List<StockPriceResponse> = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }

    suspend fun getStocksMarketInfosByTickers(request: TickersListRequest): List<StockMarketInfo> = coroutineScope {
        request.tickers.map { async { getStockMarketInfoByTicker(it) } }.awaitAll()
    }
}