package ru.dankos.yahoostockapi.controller.dto

import ru.dankos.yahoostockapi.model.Exchanges
import java.io.Serializable

class StockBaseInfoResponse(
    val ticker: String? = null,
    val companyName: String? = null,
    val exchange: Exchanges
): Serializable