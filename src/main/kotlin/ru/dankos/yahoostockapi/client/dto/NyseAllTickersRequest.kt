package ru.dankos.yahoostockapi.client.dto

data class NyseAllTickersRequest(
    val instrumentType: String = "EQUITY",
    val pageNumber: Int = 1,
    val sortColumn: String = "NORMALIZED_TICKER",
    val maxResultsPerPage: Int = 10000
)
