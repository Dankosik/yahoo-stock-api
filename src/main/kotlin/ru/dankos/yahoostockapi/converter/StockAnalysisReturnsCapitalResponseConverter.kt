package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.StockAnalysisReturnsCapitalResponse
import ru.dankos.yahoostockapi.model.ReturnsCapital

fun StockAnalysisReturnsCapitalResponse.toReturnsCapital(): ReturnsCapital {
    val response = this.data.data[0]
    return ReturnsCapital(
        ticker = response.s,
        roe = response.roe,
        roa = response.roa,
        roc = response.roic
    )
}
