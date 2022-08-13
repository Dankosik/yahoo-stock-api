package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.client.dto.YahooStockResponse
import ru.dankos.yahoostockapi.model.Exchanges
import ru.dankos.yahoostockapi.model.StockMarketInfo

fun YahooStockResponse.toStockMarketInfo(): StockMarketInfo {
    val yahooResponse = quoteSummary.result[0].price
    val currency = yahooResponse.currency
    return StockMarketInfo(
        ticker = yahooResponse.symbol,
        companyName = yahooResponse.longName,
        currency = currency,
        currencySymbol = yahooResponse.currencySymbol,
        postMarketPrice = yahooResponse.postMarketPrice.toMoneyValue(currency),
        preMarketPrice = yahooResponse.preMarketPrice?.toMoneyValue(currency),
        marketPrice = yahooResponse.regularMarketPrice.toMoneyValue(currency),
        marketDayHighPrice = yahooResponse.regularMarketDayHigh.toMoneyValue(currency),
        marketDayLowPrice = yahooResponse.regularMarketDayLow.toMoneyValue(currency),
        marketCup = yahooResponse.marketCap.toMoneyValue(currency),
        marketVolume = yahooResponse.regularMarketVolume.toMoneyValue(currency),
        exchange = if (yahooResponse.exchangeName == "NasdaqGS") Exchanges.NASDAQ else Exchanges.NYSE
    )
}