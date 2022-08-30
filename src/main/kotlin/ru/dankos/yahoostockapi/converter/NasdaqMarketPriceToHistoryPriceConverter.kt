package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.DatePrice
import ru.dankos.yahoostockapi.model.HistoryPrice
import ru.dankos.yahoostockapi.utils.convertPriceToMoneyValue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun DatePrice.toHistoryPrice() = HistoryPrice(
    value = convertPriceToMoneyValue(value.toBigDecimal(), "USD"),
    dateTime = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
)