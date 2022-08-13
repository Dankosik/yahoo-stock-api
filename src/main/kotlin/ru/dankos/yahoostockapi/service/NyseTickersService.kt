package ru.dankos.yahoostockapi.service

import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.AllTickersResponse

@Service
class NyseTickersService(
    private val cacheableStockService: CacheableStockService
) {

    suspend fun getAllAvailableTickers(): AllTickersResponse = AllTickersResponse(
        cacheableStockService.getAllAvailableTickers()
    )
}