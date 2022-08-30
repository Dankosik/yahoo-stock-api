package ru.dankos.yahoostockapi.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import ru.dankos.yahoostockapi.client.dto.NasdaqDividendResponse
import ru.dankos.yahoostockapi.client.dto.NasdaqMarketPriceResponse

@ReactiveFeignClient(name = "nasdaqApi", url = "\${feign-services.nasdaq-endpoint}")
interface NasdaqClient {

    @GetMapping("/{ticker}/dividends?assetclass=stocks")
    fun getDividendInfoByTicker(@PathVariable("ticker") ticker: String): Mono<NasdaqDividendResponse>

    @GetMapping("/{ticker}/chart?assetclass=stocks")
    fun getHistoricalPriceByTicker(
        @PathVariable("ticker") ticker: String,
        @RequestParam("fromdate") firstDate: String,
        @RequestParam("todate") secondDate: String
    ): Mono<NasdaqMarketPriceResponse>
}