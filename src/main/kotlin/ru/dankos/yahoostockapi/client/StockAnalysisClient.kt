package ru.dankos.yahoostockapi.client

import org.springframework.web.bind.annotation.GetMapping
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.dto.NyseAllTickersResponse

@ReactiveFeignClient(name = "nyse", url = "\${feign-services.stockanalysis-endpoint}")
interface StockAnalysisClient {

    @GetMapping("/wp-json/sa/select?index=allstocks&main=marketCap&count=10000&columns=no,s,marketCap,ch1w,country,ch1y,ch3y,ch5y,chYTD&page=1")
    fun getAllAvailableTickers(): Mono<NyseAllTickersResponse>
}