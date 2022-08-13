package ru.dankos.yahoostockapi.controller.dto

import ru.dankos.yahoostockapi.model.Exchanges

class StockBaseInfoResponse(
    val ticker: String,
    val companyName: String,
    val exchange: Exchanges
)