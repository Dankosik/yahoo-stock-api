package ru.dankos.yahoostockapi.client.dto

import java.io.Serializable

class NasdaqMarketPriceResponse(
    val data: Value?,
) : Serializable

class Value(
    val chart: List<Info>? = null
) : Serializable

class Info(
    val z: DatePrice
) : Serializable

class DatePrice(
    val value: String,
    val dateTime: String,
) : Serializable