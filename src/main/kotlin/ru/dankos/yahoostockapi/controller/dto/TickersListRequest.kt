package ru.dankos.yahoostockapi.controller.dto

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Size

@Validated
data class TickersListRequest(
    @field:Size(min = 1, max = 50, message = "Max size of tickers is 50")
    val tickers: List<String>
)