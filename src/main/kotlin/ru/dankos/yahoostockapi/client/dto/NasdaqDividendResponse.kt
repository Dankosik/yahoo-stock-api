package ru.dankos.yahoostockapi.client.dto

data class NasdaqDividendResponse(
    val data: Response
)

class Response(
    val dividends: Dividends? = null
)

class Dividends(
    val rows: List<Rows>? = null
)

class Rows(
    val exOrEffDate: String,
    val amount: String,
    val recordDate: String,
    val paymentDate: String,
    val currency: String,
)