package ru.dankos.yahoostockapi.client.dto

import java.io.Serializable

data class NyseAllTickersResponse(
    val data: NyseData,
) : Serializable

data class NyseData(
    val data: List<Ticker>
) : Serializable

class Ticker(
    val s: String,
    val ch1w: String? = null,
    val ch1y: String? = null,
    val ch3y: String? = null,
    val ch5y: String? = null,
    val chYTD: String? = null
): Serializable