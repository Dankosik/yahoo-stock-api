package ru.dankos.yahoostockapi.client.dto

data class YahooStockResponse(
    val quoteSummary: QuoteSummary
)

data class QuoteSummary(
    val result: List<Result>,
)

data class Result(
    val price: Price,
)

class Price(
    val symbol: String? = null,
    val regularMarketPrice: MarketData,
    val preMarketPrice: MarketData,
    val postMarketPrice: MarketData,
    val regularMarketDayHigh: MarketData,
    val regularMarketDayLow: MarketData,
    val currency: String? = null,
    val exchangeName: String? = null,
    val longName: String? = null,
    val currencySymbol: String? = null,
    val marketCap: MarketDataLongFormat,
    val regularMarketVolume: MarketDataLongFormat,
)

class MarketData(
    val fmt: String? = null,
)

class MarketDataLongFormat(
    val longFmt: String? = null,
)