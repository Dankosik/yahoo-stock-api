package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class YahooMarketDataInfoService(
    private val cacheStockService: CacheStockService
) {
    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo =
        cacheStockService.getStockMarketInfoByTicker(ticker).awaitSingle()

    suspend fun getStocksMarketInfosByTickers(request: TickersListRequest): List<StockMarketInfo> = coroutineScope {
        request.tickers.map { async { getStockMarketInfoByTicker(it) } }.awaitAll()
    }
}