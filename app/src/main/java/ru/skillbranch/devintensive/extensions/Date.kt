package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val timeDiff = this.time - date.time
    return when {
        timeDiff in (0 * SECOND)..(1 * SECOND) || timeDiff in (-1 * SECOND)..(0 * SECOND) -> "только что"
        timeDiff in (1 * SECOND)..(45 * SECOND) -> "через несколько секунд"
        timeDiff in (-45 * SECOND)..(-1 * SECOND) -> "несколько секунд назад"
        timeDiff in (45 * SECOND)..(75 * SECOND) -> "через минуту"
        timeDiff in (-75 * SECOND)..(-45 * SECOND) -> "минуту назад"
        timeDiff in (75 * SECOND)..(45 * MINUTE) -> "через ${TimeUnits.MINUTE.plural((timeDiff / MINUTE).toInt())}"
        timeDiff in (-45 * MINUTE)..(-75 * SECOND) -> "${TimeUnits.MINUTE.plural((timeDiff / MINUTE).toInt())} назад"
        timeDiff in (45 * MINUTE)..(75 * MINUTE) -> "через час"
        timeDiff in (-75 * MINUTE)..(-45 * MINUTE) -> "час назад"
        timeDiff in (75 * MINUTE)..(22 * HOUR) -> "через ${TimeUnits.HOUR.plural((timeDiff / HOUR).toInt())}"
        timeDiff in (-22 * HOUR)..(-75 * MINUTE) -> "${TimeUnits.HOUR.plural((timeDiff / HOUR).toInt())} назад"
        timeDiff in (22 * HOUR)..(26 * HOUR) -> "через день"
        timeDiff in (-26 * HOUR)..(-22 * HOUR) -> "день назад"
        timeDiff in (26 * HOUR)..(360 * DAY) -> "через ${TimeUnits.DAY.plural((timeDiff / DAY).toInt())}"
        timeDiff in (-360 * DAY).. (-26 * HOUR) -> "${TimeUnits.DAY.plural((timeDiff / DAY).toInt())} назад"
        timeDiff > (360 * DAY) ->  "более чем через год"
        else -> "более года назад"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(inputValue: Int): String {
        var base: String
        val value = abs(inputValue)
        // остаток от деления
        val remainder = value % 10

        base = when (this) {
            SECOND -> "секунд"
            MINUTE -> "минут"
            HOUR -> "час"
            DAY -> "д"
        }

        when (this) {
            SECOND, MINUTE -> {
                base = when {
                    value in 5..20 -> base
                    remainder == 1 -> base + "у"
                    remainder in 2..4 -> base + "ы"
                    else -> base
                }
            }
            HOUR -> {
                base = when {
                    value in 5..20 -> base + "ов"
                    remainder == 1 -> base
                    remainder in 2..4 -> base + "а"
                    else -> base + "ов"
                }
            }
            DAY -> {
                base = when {
                    value in 5..20 -> "${base}ней"
                    remainder == 1 -> "${base}ень"
                    remainder in 2..4 -> "${base}ня"
                    else -> base + "ней"
                }
            }
        }

        return "$value $base"
    }
}