package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.YahooStockResponse
import ru.dankos.yahoostockapi.controller.dto.MoneyValue
import ru.dankos.yahoostockapi.model.Exchanges
import ru.dankos.yahoostockapi.model.StockMarketInfo

fun YahooStockResponse.toStockMarketInfo(): StockMarketInfo {
    val yahooResponse = quoteSummary.result[0].price
    val currency = yahooResponse.currency
    val postMarketPrice = yahooResponse.postMarketPrice.toMoneyValue(currency)
    val preMarketPrice = yahooResponse.preMarketPrice.toMoneyValue(currency)
    val marketPrice = yahooResponse.regularMarketPrice.toMoneyValue(currency)
    val marketDayHighPrice = yahooResponse.regularMarketDayHigh.toMoneyValue(currency)
    val marketDayLowPrice = yahooResponse.regularMarketDayLow.toMoneyValue(currency)
    val marketCup = yahooResponse.marketCap.toMoneyValue(currency)
    val marketVolume = yahooResponse.regularMarketVolume.toMoneyValue(currency)
    return StockMarketInfo(
        ticker = yahooResponse.symbol,
        companyName = yahooResponse.longName,
        currency = currency,
        currencySymbol = yahooResponse.currencySymbol,
        postMarketPrice = postMarketPrice,
        preMarketPrice = preMarketPrice,
        marketPrice = marketPrice,
        marketDayHighPrice = marketDayHighPrice,
        marketDayLowPrice = marketDayLowPrice,
        marketCup = marketCup,
        marketVolume = marketVolume,
        exchange = if (yahooResponse.exchangeName == "NasdaqGS") Exchanges.NASDAQ else Exchanges.NYSE,
        prettyPostMarketPrice = postMarketPrice.toPrettyDouble(),
        prettyPreMarketPrice = preMarketPrice.toPrettyDouble(),
        prettyMarketPrice = marketPrice.toPrettyDouble(),
        prettyMarketDayHighPrice = marketDayHighPrice.toPrettyDouble(),
        prettyMarketDayLowPrice = marketDayLowPrice.toPrettyDouble(),
        prettyMarketCup = marketCup.toPrettyDouble(true),
        prettyMarketVolume = marketVolume.toPrettyDouble(true),
    )
}

fun MoneyValue.toPrettyDouble(roundAfterDot: Boolean = false) = if (value == null || minorUnits == null) {
    null
} else {
    val result = (value.toDouble() / minorUnits).toBigDecimal().toPlainString()
    if (roundAfterDot) {
        roundAfterDot(result)
    } else {
        result
    }
}

private fun roundAfterDot(string: String) = string.asSequence()
    .takeWhile { it != '.' }
    .joinToString(separator = "")