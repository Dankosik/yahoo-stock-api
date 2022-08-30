package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.client.NasdaqClient
import ru.dankos.yahoostockapi.client.StockAnalysisApiClient
import ru.dankos.yahoostockapi.client.YahooClient
import ru.dankos.yahoostockapi.converter.toDividendInfo
import ru.dankos.yahoostockapi.converter.toHistoryPrice
import ru.dankos.yahoostockapi.converter.toReturnsCapital
import ru.dankos.yahoostockapi.converter.toStockMarketInfo
import ru.dankos.yahoostockapi.model.DividendInfo
import ru.dankos.yahoostockapi.model.HistoryPrice
import ru.dankos.yahoostockapi.model.ReturnsCapital
import ru.dankos.yahoostockapi.model.StockMarketInfo

@Service
class CacheableStockService(
    private val yahooClient: YahooClient,
    private val stockanalysisApiClient: StockAnalysisApiClient,
    private val nasdaqClient: NasdaqClient,
) {

    @Cacheable(value = ["marketData"])
    suspend fun getStockMarketInfoByTicker(ticker: String): StockMarketInfo =
        yahooClient.getStockMarketInfoByTicker(ticker).awaitSingle().toStockMarketInfo()

    @Cacheable(value = ["returnCapital"])
    suspend fun getReturnsCapital(ticker: String): ReturnsCapital =
        stockanalysisApiClient.getReturnsCapital(ticker).awaitSingle().toReturnsCapital()

    @Cacheable(value = ["historyDividends"])
    suspend fun getHistoryPriceByTicker(ticker: String, firstDate: String, secondDate: String): HistoryPrice =
        nasdaqClient.getHistoricalPriceByTicker(ticker, firstDate, secondDate).awaitSingle().data.chart!![0].z.toHistoryPrice()

    @Cacheable(value = ["dividends"])
    suspend fun getStockDividendInfoByTicker(ticker: String): List<DividendInfo> =
        nasdaqClient.getDividendInfoByTicker(ticker).awaitSingle().data.dividends!!.rows
            ?.map { it.toDividendInfo() }
            ?.toList()
            ?: emptyList()

    @Cacheable(value = ["tickers"])
    suspend fun getAllAvailableTickers(): List<String> =
        stockanalysisApiClient.getAllAvailableTickers().awaitSingle().data.data.asSequence()
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