package ru.dankos.yahoostockapi.client.dto

import java.io.Serializable

data class NyseAllTickersRequest(
    val instrumentType: String = "EQUITY",
    val pageNumber: Int = 1,
    val sortColumn: String = "NORMALIZED_TICKER",
    val maxResultsPerPage: Int = 10000
) : Serializable
