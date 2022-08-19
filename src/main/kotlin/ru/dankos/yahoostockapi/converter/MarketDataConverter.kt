package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.MarketData
import ru.dankos.yahoostockapi.client.dto.MarketDataLongFormat
import ru.dankos.yahoostockapi.controller.dto.MoneyValue

fun MarketData.toMoneyValue(currency: String?): MoneyValue = if (fmt == null) {
    MoneyValue()
} else MoneyValue(
    value = (fmt.replace(",", "").toDouble() * 100).toLong(),
    minorUnits = 100,
    currency = currency
)

fun MarketDataLongFormat.toMoneyValue(currency: String?) = if (longFmt == null) {
    MoneyValue()
} else MoneyValue(
    value = (longFmt.replace(",", "").toDouble() * 100).toLong(),
    minorUnits = 100,
    currency = currency
)