package ru.dankos.yahoostockapi.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.dto.NyseAllTickersResponse
import ru.dankos.yahoostockapi.client.dto.StockAnalysisReturnsCapitalResponse

@ReactiveFeignClient(name = "nyse", url = "\${feign-services.stock-analysis-endpoint}")
interface StockAnalysisApiClient {

    @GetMapping("/wp-json/sa/select?index=allstocks&main=marketCap&count=10000&columns=no,s,marketCap,ch1w,country,ch1y,ch3y,ch5y,chYTD&page=1")
    fun getAllAvailableTickers(): Mono<NyseAllTickersResponse>

    @GetMapping("/wp-json/sa/select?index=allstocks&main=marketCap&count=1000&sort=desc&columns=s,marketCap,roe,roa,roic")
    fun getReturnsCapital(@RequestParam("search") ticker: String): Mono<StockAnalysisReturnsCapitalResponse>
}