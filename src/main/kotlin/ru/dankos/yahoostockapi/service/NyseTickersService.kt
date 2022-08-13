package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.AllTickersResponse

@Service
class NyseTickersService(
    private val cacheStockService: CacheStockService
) {

    suspend fun getAllAvailableTickers(): AllTickersResponse = AllTickersResponse(
        cacheStockService.getAllAvailableTickers().awaitSingle().map { it.normalizedTicker }
    )
}