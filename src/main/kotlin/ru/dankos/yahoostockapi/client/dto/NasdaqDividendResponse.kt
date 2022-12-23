package ru.dankos.yahoostockapi.client.dto

import java.io.Serializable

data class NasdaqDividendResponse(
    val data: Response
) : Serializable

class Response(
    val dividends: Dividends? = null
) : Serializable

class Dividends(
    val rows: List<Rows>? = null
) : Serializable

class Rows(
    val exOrEffDate: String,
    val amount: String,
    val recordDate: String,
    val paymentDate: String,
    val currency: String,
) : Serializable