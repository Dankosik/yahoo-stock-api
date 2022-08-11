package ru.dankos.yahoostockapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.service.YahooPriceService

@RestController
@RequestMapping("/stocks")
class YahooStockController(
    private val yahooPriceService: YahooPriceService,
) {

    @GetMapping("/{ticker}/price")
    suspend fun getMoexStockPriceByTicker(@PathVariable ticker: String): StockPriceResponse =
        yahooPriceService.getStockPriceByTicker(ticker)

    @GetMapping("/price")
    suspend fun getMoexStocksPriceByTickers(@RequestBody request: TickersListRequest): List<StockPriceResponse> =
        yahooPriceService.getMoexStocksByTickers(request)
}