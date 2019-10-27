package personal.ciai.vetclinic.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

object ScheduleUtils {

    // Veterinarian
    val `start at time` = LocalDateTime.now().withDayOfMonth(1).withHour(6).plusMonths(1).toEpochSecond(ZoneOffset.UTC)

    val `end at time` =
        LocalDateTime.now().withDayOfMonth(1).withHour(6).plusMonths(1).plusHours(1).toEpochSecond(ZoneOffset.UTC)

    val `schedule 1` = Schedules(
        id = 1,
        timeSlot = TimeSlot(`start at time`, `end at time`),
        veterinarian = `veterinarian 1`,
        status = ScheduleStatus.Available
    )

    val `schedule 2` = Schedules(
        id = 2,
        timeSlot = TimeSlot(
            LocalDateTime.now().withDayOfMonth(1).withHour(6).plusMonths(1).plusHours(1).toEpochSecond(ZoneOffset.UTC),
            LocalDateTime.now().withDayOfMonth(1).withHour(6).plusMonths(1).plusHours(2).toEpochSecond(ZoneOffset.UTC)
        ),
        veterinarian = `veterinarian 1`,
        status = ScheduleStatus.Available
    )
    val `list of schedules` = listOf<Schedules>(`schedule 1`, `schedule 2`)

    /**
     * Generate a complete month worth of schedule for the next date
     * @param starDate the first schedule slot
     */
    fun generateMonth(vet: Veterinarian): MutableList<Schedules> {
        var time = LocalDateTime.now().withDayOfMonth(1).withHour(6).plusMonths(1)
        val `list of schedules` = mutableListOf<Schedules>()
        var hoursWeek = 0
        var hoursDay = 0
        var toPerDay = ((Math.random() * ((6) + 1)) + 6).toInt()
        for (i in 1..160) {
            val time2 = time.plusHours(1)
            `list of schedules`.add(
                Schedules(
                    id = -1,
                    timeSlot = TimeSlot(
                        time.toEpochSecond(ZoneOffset.UTC),
                        time2.toEpochSecond(ZoneOffset.UTC)
                    ),
                    veterinarian = vet,
                    status = ScheduleStatus.Available
                )
            )
            hoursDay++
            hoursWeek++

            if (hoursWeek == 40) {
                time = time.withHour(6)
                time = time.plusDays(2)
                hoursWeek = 0
                hoursDay = 0
                toPerDay = ((Math.random() * ((6) + 1)) + 6).toInt()
            }

            if (hoursDay == toPerDay) {
                time = time.plusDays(1)
                hoursDay = 0
                if (toPerDay > 6)
                    time = time.withHour(8)
                else {
                    time = time.withHour(6)
                }
                toPerDay = ((Math.random() * ((6) + 1)) + 6).toInt()
            } else {
                time = time2
            }
        }
        return `list of schedules`
    }

    fun assertScheduleEquals(s1: Schedules, s2: Schedules) {
        assertAll("Is the Schedule the same?",
            { Assertions.assertEquals(s1.veterinarian, s2.veterinarian) },
            { Assertions.assertEquals(s1.status, s2.status) },
            { Assertions.assertEquals(s1.timeSlot.startDate, s2.timeSlot.startDate) },
            { Assertions.assertEquals(s1.timeSlot.endDate, s2.timeSlot.endDate) }
        )
    }
}
