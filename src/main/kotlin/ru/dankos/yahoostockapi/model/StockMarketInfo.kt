package ru.dankos.yahoostockapi.model

import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import java.io.Serializable

class StockMarketInfo(
    val ticker: String? = null,
    val companyName: String? = null,
    val currency: String? = null,
    val exchange: Exchanges,
    val currencySymbol: String? = null,
    val postMarketPrice: MoneyValue,
    val prettyPostMarketPrice: String? = null,
    val preMarketPrice: MoneyValue,
    val prettyPreMarketPrice: String? = null,
    val marketPrice: MoneyValue,
    val prettyMarketPrice: String? = null,
    val marketDayHighPrice: MoneyValue,
    val prettyMarketDayHighPrice: String? = null,
    val marketDayLowPrice: MoneyValue,
    val prettyMarketDayLowPrice: String? = null,
    val marketCup: MoneyValue,
    val prettyMarketCup: String? = null,
    val marketVolume: MoneyValue,
    val prettyMarketVolume: String? = null,
): Serializable