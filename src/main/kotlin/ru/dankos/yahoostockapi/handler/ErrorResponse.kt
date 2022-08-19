package ru.dankos.yahoostockapi.handler

class ErrorResponse(
    val errorMessage: String?,
    val errorCode: Int,
    val httpStatus: String,
)
