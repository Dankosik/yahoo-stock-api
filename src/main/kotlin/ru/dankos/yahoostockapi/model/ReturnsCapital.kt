package ru.dankos.yahoostockapi.model

import java.io.Serializable

class ReturnsCapital(
    val ticker: String,
    val roe: Double,
    val roa: Double,
    val roc: Double,
) : Serializable