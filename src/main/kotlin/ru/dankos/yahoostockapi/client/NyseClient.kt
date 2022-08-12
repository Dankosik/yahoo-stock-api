package ru.dankos.yahoostockapi.client

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.dto.NyseAllTickersRequest
import ru.dankos.yahoostockapi.client.dto.Ticker

@ReactiveFeignClient(name = "nyse", url = "\${feign-services.nyse-endpoint}")
interface NyseClient {

    @PostMapping("/quotes/filter")
    fun getStockMarketInfoByTicker(@RequestBody nyseAllTickersRequest: NyseAllTickersRequest): Mono<List<Ticker>>
}