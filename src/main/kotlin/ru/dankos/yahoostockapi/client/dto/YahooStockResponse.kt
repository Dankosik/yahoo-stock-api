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
    val regularMarketPrice: RegularMarketPrice,
    val currency: String,
)

data class RegularMarketPrice(
    val raw: Double,
    val fmt: String,
)
