package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.client.StockAnalysisClient
import ru.dankos.yahoostockapi.client.YahooClient
import ru.dankos.yahoostockapi.converter.toStockMarketInfo
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class CacheableStockService(
    private val yahooClient: YahooClient,
    private val stockanalysisClient: StockAnalysisClient,
) {

    @Cacheable(value = ["marketData"])
    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo =
        yahooClient.getStockMarketInfoByTicker(ticker).awaitSingle().toStockMarketInfo()

    @Cacheable(value = ["tickers"])
    suspend fun getAllAvailableTickers(): List<String> =
        stockanalysisClient.getAllAvailableTickers().awaitSingle().data.data.asSequence()
            .filter { !(it.ch1w == null && it.ch1y == null && it.ch3y == null && it.ch5y == null && it.chYTD == null) }
            .map { it.s }
            .filter { it !in NOT_LISTED_YAHOO_TICKERS }
            .map { ticker ->
                val find = ticker.find { it == '.' }
                if (find == null) return@map ticker else {
                    return@map ticker.replace(find, '-')
                }
            }
            .toList()

    companion object {
        private val NOT_LISTED_YAHOO_TICKERS = listOf("MBT", "SBRCY", "SKGR", "RAZFF", "KCAC", "PTWO", "HMAC", "MOBV")
    }
}