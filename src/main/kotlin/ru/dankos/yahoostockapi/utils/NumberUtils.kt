package ru.dankos.yahoostockapi.utils

import java.math.BigDecimal
import kotlin.math.pow
import kotlin.math.roundToInt

fun countDigitsAfterPoint(number: BigDecimal): Int = if (number.toString().indexOf('.') != -1) {
    number.toString().reversed().chars().takeWhile { it.toChar() != '.' }.count().toInt()
} else {
    1
}

fun roundAfterDot(number: String) = number.asSequence()
    .takeWhile { it != '.' }
    .joinToString(separator = "")


fun roundAfterDot(number: Double) = number.toString().asSequence()
    .takeWhile { it != '.' }
    .joinToString(separator = "")

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}