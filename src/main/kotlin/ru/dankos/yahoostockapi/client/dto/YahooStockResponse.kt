package ru.dankos.yahoostockapi.client.dto

import java.io.Serializable

data class YahooStockResponse(
    val quoteSummary: QuoteSummary
) : Serializable

data class QuoteSummary(
    val result: List<Result>,
) : Serializable

data class Result(
    val price: Price,
) : Serializable

class Price(
    val symbol: String? = null,
    val regularMarketPrice: MarketData,
    val preMarketPrice: MarketData,
    val postMarketPrice: MarketData,
    val regularMarketDayHigh: MarketData,
    val regularMarketDayLow: MarketData,
    val currency: String,
    val exchangeName: String? = null,
    val longName: String? = null,
    val currencySymbol: String? = null,
    val marketCap: MarketDataLongFormat,
    val regularMarketVolume: MarketDataLongFormat,
) : Serializable

class MarketData(
    val fmt: String? = null,
) : Serializable


class MarketDataLongFormat(
    val longFmt: String? = null,
) : Serializable