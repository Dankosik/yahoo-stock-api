package ru.dankos.yahoostockapi.service

import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.controller.dto.TickersListRequest
import ru.dankos.yahoostockapi.exception.InternalException
import ru.dankos.yahoostockapi.exception.StockNotFoundException
import java.time.LocalTime

@Service
class YahooPriceService(
    private val cacheStockService: CacheStockService
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse = try {
        val response = cacheStockService.getStockPriceByTicker(ticker).awaitSingle().quoteSummary.result[0].price
        StockPriceResponse(
            ticker = response.symbol,
            moneyValue = MoneyValue(
                value = (response.regularMarketPrice.fmt.toDouble() * 100).toInt(),
                minorUnits = 100,
                currency = response.currency
            ),
            time = LocalTime.now()
        )
    } catch (e: Exception) {
        if (e is FeignException && e.status() == 404) {
            throw StockNotFoundException("Stock not found")
        }
        throw InternalException(e)
    }

    suspend fun getMoexStocksByTickers(request: TickersListRequest): List<StockPriceResponse> = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }
}