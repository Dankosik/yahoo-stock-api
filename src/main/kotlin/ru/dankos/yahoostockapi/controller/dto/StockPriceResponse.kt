package ru.dankos.yahoostockapi.controller.dto

import java.time.LocalTime

data class StockPriceResponse(
    val ticker: String,
    val moneyValue: MoneyValue,
    val time: LocalTime,
)

data class MoneyValue(
    val value: Int,
    val minorUnits: Double,
    val currency: String,
)