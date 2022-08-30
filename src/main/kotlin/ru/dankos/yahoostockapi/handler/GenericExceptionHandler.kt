package ru.dankos.yahoostockapi.handler

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import ru.dankos.yahoostockapi.exception.StockNotFoundException

@RestControllerAdvice
class GenericExceptionHandler {

//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = [Exception::class])
//    internal fun handleAnyException(exception: Exception): ErrorResponse =
//        exception.toErrorResponse(INTERNAL_SERVER_ERROR).apply { logger.error { exception } }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = [WebExchangeBindException::class])
    internal fun handleAnyWebExchangeBindException(exception: WebExchangeBindException): ErrorResponse =
        exception.toErrorResponse(BAD_REQUEST).apply { logger.error { exception } }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = [StockNotFoundException::class])
    internal fun handleEntityNotFoundException(exception: StockNotFoundException): ErrorResponse =
        exception.toErrorResponse(NOT_FOUND)

    private fun Exception.toErrorResponse(httpStatus: HttpStatus): ErrorResponse = ErrorResponse(
        errorMessage = message,
        errorCode = httpStatus.value(),
        httpStatus = httpStatus.name,
    )

    private fun WebExchangeBindException.toErrorResponse(httpStatus: HttpStatus): ErrorResponse = ErrorResponse(
        errorMessage = bindingResult.allErrors[0].defaultMessage,
        errorCode = httpStatus.value(),
        httpStatus = httpStatus.name,
    )

    companion object : KLogging()
}
