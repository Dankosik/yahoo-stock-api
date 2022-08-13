package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.StockBaseInfoResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class YahooMarketDataInfoService(
    private val cacheStockService: CacheStockService
) {
    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo =
        cacheStockService.getStockMarketInfoByTicker(ticker)

    suspend fun getStockBaseInfoResponseByTicker(ticker: String): StockBaseInfoResponse {
        val marketInfo = cacheStockService.getStockMarketInfoByTicker(ticker)
        return StockBaseInfoResponse(
            ticker = marketInfo.ticker,
            companyName = marketInfo.companyName,
            exchange = marketInfo.exchange
        )
    }

    suspend fun getStocksMarketInfosByTickers(request: TickersListRequest): List<StockMarketInfo> = coroutineScope {
        request.tickers.map { async { getStockMarketInfoByTicker(it) } }.awaitAll()
    }
}