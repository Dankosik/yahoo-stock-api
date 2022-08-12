package ru.dankos.yahoostockapi.model

import ru.dankos.yahoostockapi.controller.dto.MoneyValue

class StockMarketInfo(
    val ticker: String,
    val companyName: String,
    val currency: String,
    val currencySymbol: String,
    val postMarketPrice: MoneyValue,
    val preMarketPrice: MoneyValue?,
    val marketPrice: MoneyValue,
    val marketDayHighPrice: MoneyValue,
    val marketDayLowPrice: MoneyValue,
    val marketCup: MoneyValue,
    val marketVolume: MoneyValue,
)