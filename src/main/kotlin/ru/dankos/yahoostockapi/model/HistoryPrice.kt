package ru.dankos.yahoostockapi.model

import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import java.time.LocalDate

class HistoryPrice(
    val value: MoneyValue,
    val dateTime: LocalDate,
)