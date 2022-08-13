package ru.dankos.yahoostockapi.service

import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.controller.dto.AllTickersResponse

@Service
class NyseTickersService(
    private val cacheStockService: CacheStockService
) {

    suspend fun getAllAvailableTickers(): AllTickersResponse = AllTickersResponse(
        cacheStockService.getAllAvailableTickers()
    )
}