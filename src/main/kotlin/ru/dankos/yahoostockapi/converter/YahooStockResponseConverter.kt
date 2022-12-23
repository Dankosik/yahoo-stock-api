package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.YahooStockResponse
import ru.dankos.yahoostockapi.model.Exchanges
import ru.dankos.yahoostockapi.model.StockMarketInfo
import ru.dankos.yahoostockapi.utils.toPrettyString

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
        prettyPostMarketPrice = postMarketPrice.toPrettyString(),
        prettyPreMarketPrice = preMarketPrice.toPrettyString(),
        prettyMarketPrice = marketPrice.toPrettyString(),
        prettyMarketDayHighPrice = marketDayHighPrice.toPrettyString(),
        prettyMarketDayLowPrice = marketDayLowPrice.toPrettyString(),
        prettyMarketCup = marketCup.toPrettyString(true),
        prettyMarketVolume = marketVolume.toPrettyString(true),
    )
}