package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.Rows
import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import ru.dankos.yahoostockapi.model.DividendInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.pow

fun Rows.toDividendInfo(): DividendInfo {
    val exDate = convertStringToLocalDate(exOrEffDate)
    val amount = convertDoubleToMoneyValue(amount, currency)
    val recordDate = convertStringToLocalDate(recordDate)
    val payDate = convertStringToLocalDate(paymentDate)
    return DividendInfo(
        exDate = exDate!!,
        amount = amount,
        recordDate = recordDate!!,
        paymentDate = payDate,
    )
}

private fun convertDoubleToMoneyValue(amount: String, currency: String): MoneyValue {
    val amountDouble = convertStringAmountToDouble(amount)
    val value = amountDouble.toString().takeWhile { it == '.' }.toLong()
    val minorUnits = 10.0.pow(amountDouble.toString().takeWhile { it == '.' }.count()).toInt()
    return MoneyValue(value, minorUnits, currency)
}

private fun convertStringToLocalDate(stringDate: String): LocalDate? {
    return if (stringDate == "N/A") {
        null
    } else {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        LocalDate.parse(stringDate, formatter)
    }
}

private fun convertStringAmountToDouble(stringAmount: String): Double =
    stringAmount.drop(1).toDouble()



