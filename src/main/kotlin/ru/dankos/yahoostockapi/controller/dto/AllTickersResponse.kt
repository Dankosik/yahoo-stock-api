package ru.dankos.yahoostockapi.controller.dto

import java.io.Serializable

class AllTickersResponse(
    val tickers: List<String>
): Serializable