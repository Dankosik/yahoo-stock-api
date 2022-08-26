package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.controller.dto.StockPriceResponse
import ru.dankos.yahoostockapi.model.StockMarketInfo
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun StockMarketInfo.toStockPriceResponse() = StockPriceResponse(
    ticker = ticker,
    stockPrice = marketPrice,
    prettyStockPrice = marketPrice.toPrettyDouble(),
    time = formatter.format(ZonedDateTime.now())
)

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")