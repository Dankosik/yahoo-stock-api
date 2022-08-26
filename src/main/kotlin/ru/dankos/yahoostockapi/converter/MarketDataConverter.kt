package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.MarketData
import ru.dankos.yahoostockapi.client.dto.MarketDataLongFormat
import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import ru.dankos.yahoostockapi.utils.convertPriceToMoneyValue

fun MarketData.toMoneyValue(currency: String): MoneyValue = if (fmt == null) {
    MoneyValue()
} else {
    convertPriceToMoneyValue(convertYahooPriceToBigDecimal(fmt), currency)
}

fun MarketDataLongFormat.toMoneyValue(currency: String) = if (longFmt == null) {
    MoneyValue()
} else {
    convertPriceToMoneyValue(convertYahooPriceToBigDecimal(longFmt), currency)
}

private fun convertYahooPriceToBigDecimal(yahooPrice: String) = yahooPrice.replace(",", "").toBigDecimal()