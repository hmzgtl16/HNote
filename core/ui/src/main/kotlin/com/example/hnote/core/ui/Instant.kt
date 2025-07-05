package com.example.hnote.core.ui

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val formatter =
    LocalDateTime.Format {
        date(
            format = LocalDate.Format {
                dayOfWeek(names = DayOfWeekNames.ENGLISH_ABBREVIATED)
                char(value = Char(code = 44))
                char(value = Char(code = 32))
                monthName(names = MonthNames.ENGLISH_ABBREVIATED)
                char(value = Char(code = 32))
                dayOfMonth()
                char(value = Char(code = 44))
                char(value = Char(code = 32))
                year()
            }
        )
        char(value = Char(code = 32))
        time(
            format = LocalTime.Format {
                hour()
                char(value = Char(code = 58))
                minute()
            }
        )
    }

fun Instant.format(): String =
    toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
        .format(format = formatter)

fun Instant.isExpired(): Boolean = this <= Clock.System.now()