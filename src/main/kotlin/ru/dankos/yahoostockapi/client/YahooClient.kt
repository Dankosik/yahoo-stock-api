package ru.dankos.yahoostockapi.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.dto.YahooStockResponse

@ReactiveFeignClient(name = "yahoo", url = "\${feign-services.yahoo-endpoint}")
interface YahooClient {

    @GetMapping("/{ticker}?modules=price")
    fun getStockMarketInfoByTicker(@PathVariable("ticker") ticker: String): Mono<YahooStockResponse>
}