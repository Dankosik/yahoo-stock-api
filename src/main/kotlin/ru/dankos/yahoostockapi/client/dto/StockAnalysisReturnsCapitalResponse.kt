package ru.dankos.yahoostockapi.client.dto

import java.io.Serializable

class StockAnalysisReturnsCapitalResponse(
    val data: StockAnalysis,
) : Serializable

class StockAnalysis(
    val data: List<ReturnCapitalData>
) : Serializable

class ReturnCapitalData(
    val s: String,
    val roe: Double,
    val roa: Double,
    val roic: Double,
) : Serializable
