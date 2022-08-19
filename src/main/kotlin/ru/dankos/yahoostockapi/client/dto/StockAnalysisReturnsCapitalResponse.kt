package ru.dankos.yahoostockapi.client.dto

class StockAnalysisReturnsCapitalResponse(
    val data: StockAnalysis,
)

class StockAnalysis(
    val data: List<ReturnCapitalData>
)

class ReturnCapitalData(
    val s: String,
    val roe: Double,
    val roa: Double,
    val roic: Double,
)
