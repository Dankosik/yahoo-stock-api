package ru.dankos.yahoostockapi.model

import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import java.time.LocalDate

class DividendInfo(
    val exDate: LocalDate,
    val amount: MoneyValue,
<<<<<<< HEAD
    val recordDate: LocalDate,
=======
    val recordDate: LocalDate?,
>>>>>>> origin/get-dividend-info
    val paymentDate: LocalDate?,
)