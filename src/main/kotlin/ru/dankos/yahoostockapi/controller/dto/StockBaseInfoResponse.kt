package ru.dankos.yahoostockapi.controller.dto

import ru.dankos.yahoostockapi.model.Exchange

class StockBaseInfoResponse(
    val ticker: String,
    val companyName: String,
    val exchange: Exchange
)