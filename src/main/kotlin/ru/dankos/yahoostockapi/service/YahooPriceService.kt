package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import java.time.LocalTime

@Service
class YahooPriceService(
    private val cacheStockService: CacheStockService
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse {
        val response = cacheStockService.getStockPriceByTicker(ticker).awaitSingle().quoteSummary.result[0].price
        return StockPriceResponse(
            ticker = response.symbol,
            moneyValue = MoneyValue(
                value = response.regularMarketPrice.fmt.length,
                minorUnits = response.regularMarketPrice.raw,
                currency = response.currency
            ),
            time = LocalTime.now()
        )
    }

    suspend fun getMoexStocksByTickers(request: TickersListRequest): List<StockPriceResponse> = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }
}