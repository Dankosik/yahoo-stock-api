package ru.dankos.yahoostockapi.controller.dto

import java.io.Serializable

class DividendInfoResponse(
    val exDate: String,
    val amount: MoneyValue,
    val prettyAmount: String? = null,
    val recordDate: String,
    val paymentDate: String,
    val prettyDividendIncome: String,
    val dividendIncome: Double? = null,
): Serializable
