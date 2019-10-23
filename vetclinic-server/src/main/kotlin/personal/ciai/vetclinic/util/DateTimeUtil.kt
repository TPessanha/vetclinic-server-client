package personal.ciai.vetclinic.util

import java.time.DayOfWeek.SUNDAY
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

fun asDate(localDate: LocalDate): Date =
    Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

fun asDate(date: Long): Date = Date(date)

fun asLocalDate(date: Date): LocalDate {
    return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun notWeekends(date: Date): Boolean {
    val day = asLocalDate(date).dayOfWeek
    if (day.equals(SUNDAY))
        return false

    return true
}
