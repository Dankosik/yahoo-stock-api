package ru.dankos.yahoostockapi.controller.dto

import ru.dankos.yahoostockapi.model.Exchanges

class StockBaseInfoResponse(
    val ticker: String? = null,
    val companyName: String? = null,
    val exchange: Exchanges
)