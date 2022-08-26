package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.Rows
import ru.dankos.yahoostockapi.model.DividendInfo
import ru.dankos.yahoostockapi.utils.convertPriceToMoneyValue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Rows.toDividendInfo() = DividendInfo(
    exDate = convertNasdaqDateToLocalDate(exOrEffDate),
    amount = convertPriceToMoneyValue(convertNasdaqPriceToBigDecimal(this.amount), currency),
    recordDate = convertNasdaqDateToLocalDate(this.recordDate),
    paymentDate = convertNasdaqDateToLocalDate(paymentDate),
)

private fun convertNasdaqDateToLocalDate(nasdaqDate: String?): LocalDate? = if (nasdaqDate == "N/A") {
    null
} else {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    LocalDate.parse(nasdaqDate, formatter)
}

private fun convertNasdaqPriceToBigDecimal(nasdaqPrice: String) = nasdaqPrice.drop(1).toDouble().toBigDecimal()



