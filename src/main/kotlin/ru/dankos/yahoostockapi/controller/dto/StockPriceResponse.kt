package ru.dankos.yahoostockapi.controller.dto

import java.io.Serializable

data class StockPriceResponse(
    val ticker: String? = null,
    val stockPrice: MoneyValue? = null,
    val prettyStockPrice: String? = null,
    val time: String,
): Serializable

data class MoneyValue(
    val value: Long? = null,
    val minorUnits: Int? = null,
    val currency: String? = null,
) : Serializable