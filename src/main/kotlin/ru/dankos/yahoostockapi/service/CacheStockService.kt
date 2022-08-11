package ru.dankos.yahoostockapi.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.YahooClient
import ru.dankos.yahoostockapi.client.dto.YahooStockResponse

@Service
class CacheStockService(
    private val yahooClient: YahooClient,
) {

    @Cacheable(value = ["marketData"], key = "#ticker")
    fun getStockPriceByTicker(ticker: String): Mono<YahooStockResponse> =
        yahooClient.getStockByTicker(ticker).cache()
}