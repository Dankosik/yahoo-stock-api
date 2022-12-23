package ru.dankos.yahoostockapi.service

import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.StockBaseInfoResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class YahooMarketDataInfoService(
    private val cacheableStockService: CacheableStockService
) {
    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo = try {
        cacheableStockService.getStockMarketInfoByTicker(ticker)
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found").apply { YahooPriceService.logger.warn { "Stock not found $ticker" } }
        }
        throw InternalException(e)
    }

    @Cacheable(value = ["baseInfo"])
    suspend fun getStockBaseInfoResponseByTicker(ticker: String): StockBaseInfoResponse {
        val marketInfo = cacheableStockService.getStockMarketInfoByTicker(ticker)
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