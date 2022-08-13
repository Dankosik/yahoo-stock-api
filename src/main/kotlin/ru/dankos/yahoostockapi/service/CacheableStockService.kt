package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.client.NyseClient
import ru.dankos.yahoostockapi.client.YahooClient
import ru.dankos.yahoostockapi.client.dto.NyseAllTickersRequest
import ru.dankos.yahoostockapi.converter.toStockMarketInfo
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class CacheableStockService(
    private val yahooClient: YahooClient,
    private val nyseClient: NyseClient,
) {

    @Cacheable(value = ["marketData"])
    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo =
        yahooClient.getStockMarketInfoByTicker(ticker).awaitSingle().toStockMarketInfo()

    @Cacheable(value = ["tickers"])
    suspend fun getAllAvailableTickers(): List<String> =
        nyseClient.getAllAvailableTickers(NyseAllTickersRequest()).awaitSingle().map { it.normalizedTicker }
}