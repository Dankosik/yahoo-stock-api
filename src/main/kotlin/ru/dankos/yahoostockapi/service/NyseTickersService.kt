package ru.dankos.yahoostockapi.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.yahoostockapi.client.NyseClient
import ru.dankos.yahoostockapi.client.dto.NyseAllTickersRequest
import ru.dankos.yahoostockapi.controller.dto.AllTickersResponse

@Service
class NyseTickersService(
    private val nyseClient: NyseClient
) {

    suspend fun getAllAvailableTickers(): AllTickersResponse = AllTickersResponse(
        nyseClient.getStockMarketInfoByTicker(NyseAllTickersRequest()).awaitSingle().map { it.normalizedTicker }
    )
}