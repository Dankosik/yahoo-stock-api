package ru.dankos.yahoostockapi.client.dto

class NasdaqMarketPriceResponse(
    val data: Value?,
)

class Value(
    val chart: List<Info>? = null
)

class Info(
    val z: DatePrice
)

class DatePrice(
    val value: String,
    val dateTime: String,
)