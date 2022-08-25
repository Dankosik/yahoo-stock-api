package ru.dankos.yahoostockapi.converter

import ru.dankos.yahoostockapi.controller.dto.DividendInfoResponse
import ru.dankos.yahoostockapi.model.DividendInfo

fun DividendInfo.toDividendInfoResponse(): DividendInfoResponse {
    if (paymentDate == null) {
        val strPayDate = "N/A"
        return DividendInfoResponse(
            exDate = exDate.toString(),
            amount = amount.toString(),
            recordDate = recordDate.toString(),
            paymentDate = strPayDate,
        )
    }

    return DividendInfoResponse(
        exDate = exDate.toString(),
        amount = amount.toString(),
        recordDate = recordDate.toString(),
        paymentDate = paymentDate.toString(),

    )
}
