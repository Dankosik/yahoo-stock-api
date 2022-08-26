package ru.dankos.yahoostockapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dankos.yahoostockapi.controller.dto.AllTickersResponse
import ru.dankos.yahoostockapi.controller.dto.DividendInfoResponse
import ru.dankos.yahoostockapi.controller.dto.StockBaseInfoResponse
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.model.ReturnsCapital
import ru.dankos.yahoostockapi.model.StockMarketInfo
import ru.dankos.yahoostockapi.service.NyseTickersService
import ru.dankos.yahoostockapi.service.StockAnalysisService
import ru.dankos.yahoostockapi.service.YahooMarketDataInfoService
import ru.dankos.yahoostockapi.service.YahooPriceService
import javax.validation.Valid

@RestController
@RequestMapping("/stocks")
class YahooStockController(
    private val yahooPriceService: YahooPriceService,
    private val yahooMarketDataInfoService: YahooMarketDataInfoService,
    private val tickerService: NyseTickersService,
    private val stockAnalysisService: StockAnalysisService,
) {

    @GetMapping("/{ticker}/price")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): StockPriceResponse =
        yahooPriceService.getStockPriceByTicker(ticker)

    @GetMapping("/{ticker}/marketInfo")
    suspend fun getStockMarketInfoByTicker(@PathVariable ticker: String): StockMarketInfo =
        yahooMarketDataInfoService.getStockMarketInfoByTicker(ticker)

    @GetMapping("/{ticker}/baseInfo")
    suspend fun getStockBaseInfoResponseByTicker(@PathVariable ticker: String): StockBaseInfoResponse =
        yahooMarketDataInfoService.getStockBaseInfoResponseByTicker(ticker)

    @GetMapping("/{ticker}/historyDiv")
    suspend fun getHistoricalDividendInfoResponseByTicker(@PathVariable ticker: String): List<DividendInfoResponse> =
        stockAnalysisService.getStockHistoricalDividendsByTicker(ticker)

    @GetMapping("/historyDiv")
    suspend fun getHistoricalDividendInfoResponseByTickers(@Valid @RequestBody request: TickersListRequest): List<List<DividendInfoResponse>> =
        stockAnalysisService.getStockHistoricalDividendsByTickers(request)

    @GetMapping("/{ticker}/futureDiv")
    suspend fun getFutureDividendInfoResponseByTicker(@PathVariable ticker: String): List<DividendInfoResponse> =
        stockAnalysisService.getStockFutureDividendsByTicker(ticker)

    @GetMapping("/futureDiv")
    suspend fun getFutureDividendInfoResponseByTickers(@Valid @RequestBody request: TickersListRequest): List<List<DividendInfoResponse>> =
        stockAnalysisService.getStockFutureDividendsByTickers(request)

    @GetMapping("/marketInfo")
    suspend fun getStocksMarketInfosByTickers(@Valid @RequestBody request: TickersListRequest): List<StockMarketInfo> =
        yahooMarketDataInfoService.getStocksMarketInfosByTickers(request)

    @GetMapping("/{ticker}/returnCapital")
    suspend fun getReturnsCapital(@PathVariable ticker: String): ReturnsCapital =
        stockAnalysisService.getReturnsCapital(ticker)

    @GetMapping("/price")
    suspend fun getStocksByTickers(@Valid @RequestBody request: TickersListRequest): List<StockPriceResponse> =
        yahooPriceService.getStocksByTickers(request)

    @GetMapping("/tickers")
    suspend fun getAllAvailableTickers(): AllTickersResponse =
        tickerService.getAllAvailableTickers()
}