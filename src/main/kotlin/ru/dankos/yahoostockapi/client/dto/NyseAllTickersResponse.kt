package ru.dankos.yahoostockapi.client.dto

data class Ticker(
    val total: Int,
    val url: String,
    val exchangeId: String,
    val instrumentType: String,
    val symbolTicker: String,
    val symbolExchangeTicker: String,
    val symbolEsignalTicker: String,
    val instrumentName: String,
    val micCode: String,
    val normalizedTicker: String,
)