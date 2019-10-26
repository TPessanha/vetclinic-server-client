package personal.ciai.vetclinic.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.Date

fun asDate(localDate: LocalDate): Date =
    Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

fun asDate(date: Long): Date = Date(date)

fun now(): Date = Date.from(Instant.now())

fun asLocalDate(date: Date): LocalDate {
    return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun asLocalDate(date: Long): LocalDate {
    return Instant.ofEpochMilli(asDate(date).time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun toHours(mills: Long): Int {
    return ((mills / 1000) / 60 / 60).toInt()
}

fun sameDate(date1: LocalDate, date2: Long) = date1.dayOfMonth == (asLocalDate(date2).dayOfMonth)

fun numberOfWeeks(date: LocalDateTime) = YearMonth.from(date).atEndOfMonth().get(
    WeekFields.ISO.weekOfMonth()
)

fun isValidateDate(date: LocalDateTime): Boolean {
    val current = YearMonth.now()
    return current.year <= date.year && current.month == date.month - 1
}
