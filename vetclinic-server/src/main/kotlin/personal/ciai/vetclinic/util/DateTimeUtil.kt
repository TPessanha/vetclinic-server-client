package personal.ciai.vetclinic.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

fun asDate(localDate: LocalDate): Date =
    Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

fun asDate(date: Long): Date = Date(date)

fun now(): Date = Date.from(Instant.now())

fun asLocalDate(date: Date): LocalDate {
    return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun toHours(mills: Long): Int {
    return ((mills / 1000) / 60 / 60).toInt()
}

fun sameDate(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(
        Calendar.YEAR
    )
}
