package ru.dankos.yahoostockapi.service

import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.model.ReturnsCapital

@Service
class StockAnalysisService(
    private val cacheableStockService: CacheableStockService
) {

    suspend fun getReturnsCapital(ticker: String): ReturnsCapital = cacheableStockService.getReturnsCapital(ticker)
}