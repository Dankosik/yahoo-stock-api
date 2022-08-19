package ru.dankos.yahoostockapi.controller.dto

data class StockPriceResponse(
    val ticker: String? = null,
    val stockPrice: MoneyValue? = null,
    val prettyStockPrice: String? = null,
    val time: String,
)

data class MoneyValue(
    val value: Long? = null,
    val minorUnits: Int? = null,
    val currency: String? = null,
)