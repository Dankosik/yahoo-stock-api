package ru.dankos.yahoostockapi.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.NyseClient
import ru.dankos.yahoostockapi.client.YahooClient
import ru.dankos.yahoostockapi.client.dto.NyseAllTickersRequest
import ru.dankos.yahoostockapi.client.dto.Ticker
import ru.dankos.yahoostockapi.controller.dto.AllTickersResponse
import ru.dankos.yahoostockapi.converter.toStockMarketInfo
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class CacheStockService(
    private val yahooClient: YahooClient,
    private val nyseClient: NyseClient,
) {

    @Cacheable(value = ["marketData"], key = "#ticker")
    fun getStockMarketInfoByTicker(ticker: String): Mono<StockMarketInfo> =
        yahooClient.getStockMarketInfoByTicker(ticker).map { it.toStockMarketInfo() }.cache()

    @Cacheable(value = ["tickers"], key = "#root.target.STATIC_TICKERS_KEY")
    suspend fun getAllAvailableTickers(): Mono<List<Ticker>> =
        nyseClient.getAllAvailableTickers(NyseAllTickersRequest()).cache()

    companion object{
        const val STATIC_TICKERS_KEY = "TICKERS"
    }
}