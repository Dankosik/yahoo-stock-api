package ru.dankos.yahoostockapi.utils

import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import java.math.BigDecimal
import kotlin.math.pow

fun convertPriceToMoneyValue(price: BigDecimal?, currency: String): MoneyValue {
    if (price == null) {
        return MoneyValue()
    }
    var exchangeUnits = 10.0.pow(countDigitsAfterPoint(price)).toInt()
    var value = (price * exchangeUnits.toBigDecimal()).toLong()
    if (exchangeUnits < 100) {
        exchangeUnits *= 10
        value *= 10
    }
    return MoneyValue(value, exchangeUnits, currency)
}

fun MoneyValue.toPrettyString(roundAfterDot: Boolean = false) = if (value == null || minorUnits == null) {
    null
} else {
    val result = (value.toDouble() / minorUnits).toBigDecimal().toPlainString()
    if (roundAfterDot) {
        roundAfterDot(result)
    } else {
        result
    }
}

