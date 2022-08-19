package ru.dankos.yahoostockapi.client.dto

data class NyseAllTickersResponse(
    val data: Data,
)

data class Data(
    val data: List<Ticker>
)

class Ticker(
    val s: String,
    val ch1w: String? = null,
    val ch1y: String? = null,
    val ch3y: String? = null,
    val ch5y: String? = null,
    val chYTD: String? = null
)