package ru.dankos.yahoostockapi.model

import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import java.time.LocalDate

class DividendInfo(
    val exDate: LocalDate?,
    val amount: MoneyValue,
    val recordDate: LocalDate?,
    val paymentDate: LocalDate?,
)