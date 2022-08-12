package ru.dankos.yahoostockapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.model.StockMarketInfo
import ru.dankos.yahoostockapi.service.YahooMarketDataInfoService
import ru.dankos.yahoostockapi.service.YahooPriceService

@RestController
@RequestMapping("/stocks")
class YahooStockController(
    private val yahooPriceService: YahooPriceService,
    private val yahooMarketDataInfoService: YahooMarketDataInfoService,
) {

    @GetMapping("/{ticker}/price")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): StockPriceResponse =
        yahooPriceService.getStockPriceByTicker(ticker)

    @GetMapping("/{ticker}/marketInfo")
    suspend fun getStockMarketInfoByTicker(@PathVariable ticker: String): StockMarketInfo =
        yahooMarketDataInfoService.getStockMarketInfoByTicker(ticker)

    @GetMapping("/marketInfo")
    suspend fun getStocksMarketInfosByTickers(@RequestBody request: TickersListRequest): List<StockMarketInfo> =
        yahooMarketDataInfoService.getStocksMarketInfosByTickers(request)

    @GetMapping("/price")
    suspend fun getMoexStocksPriceByTickers(@RequestBody request: TickersListRequest): List<StockPriceResponse> =
        yahooPriceService.getStocksByTickers(request)
}