package personal.ciai.vetclinic.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.WeekFields
import java.util.Date

fun asDate(date: Long): Date = Date(date)

fun now(): Date = Date.from(Instant.now())

fun asLocalDate(date: Date): LocalDate {
    return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun asLocalDate(date: Long): LocalDate {
    return Instant.ofEpochMilli(asDate(date).time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun asLocalDateTime(date: Long): LocalDateTime {
    return LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC)
}

fun toHours(mills: Long): Int {
    return ((mills) / 60 / 60).toInt()
}

fun numberOfWeeks(date: LocalDateTime) = YearMonth.from(date).atEndOfMonth().get(
    WeekFields.ISO.weekOfMonth()
)

fun isValidateDate(date: LocalDateTime): Boolean {
    val current = YearMonth.now()
    return (current.year == date.year && current.monthValue <= date.monthValue) || (current.year < date.year)
}

fun isBefore(toLong: Long): Boolean {
    return asLocalDateTime(toLong).toInstant(ZoneOffset.UTC).isBefore(LocalDateTime.now().toInstant(ZoneOffset.UTC))
}
