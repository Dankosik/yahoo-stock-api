package ru.dankos.yahoostockapi.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.YahooClient
import ru.dankos.yahoostockapi.converter.toStockMarketInfo
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class CacheStockService(
    private val yahooClient: YahooClient,
) {

    @Cacheable(value = ["marketData"], key = "#ticker")
    fun getStockMarketInfoByTicker(ticker: String): Mono<StockMarketInfo> =
        yahooClient.getStockMarketInfoByTicker(ticker).map { it.toStockMarketInfo() }.cache()
}