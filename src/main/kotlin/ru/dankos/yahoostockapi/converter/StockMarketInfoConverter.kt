package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.model.StockMarketInfo
import java.time.LocalTime

fun StockMarketInfo.toStockPriceResponse() = StockPriceResponse(
    ticker = ticker,
    stockPrice = marketPrice,
    time = LocalTime.now()
)