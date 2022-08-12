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
    val symbol: String,
    val regularMarketPrice: MarketData,
    val preMarketPrice: MarketData? = null,
    val postMarketPrice: MarketData,
    val regularMarketDayHigh: MarketData,
    val regularMarketDayLow: MarketData,
    val currency: String,
    val longName: String,
    val currencySymbol: String,
    val marketCap: MarketDataLongFormat,
    val regularMarketVolume: MarketDataLongFormat,
)

data class MarketData(
    val raw: Double? = null,
    val fmt: String? = null,
)

data class MarketDataLongFormat(
    val raw: Double,
    val fmt: String,
    val longFmt: String,
)
