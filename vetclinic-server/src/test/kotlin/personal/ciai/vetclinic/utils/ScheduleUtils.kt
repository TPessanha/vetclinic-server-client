package personal.ciai.vetclinic.utils

import java.util.BitSet
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.assertAll
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 2`

object ScheduleUtils {

    val blocks = BitSet(720)
    val booked = BitSet(720)

    val `schedule 1` = Schedule(
        id = 1,
        month = 11,
        year = 2019,
        veterinarian = `veterinarian 1`,
        availableBlocks = blocks.toByteArray(),
        bookedBlocks = booked.toByteArray()
    )

    val `schedule 2` = Schedule(
        1, 11, 2019, `veterinarian 2`, blocks.toByteArray(), booked.toByteArray()
    )

    val `list of schedules` = listOf<Schedule>(`schedule 1`, `schedule 2`)

    fun assertScheduleEquals(s1: Schedule, s2: Schedule) {
        assertAll("Is the Schedule the same?",
            { Assertions.assertEquals(s1.veterinarian, s2.veterinarian) }
//            { Assertions.assertEquals(s1.status, s2.status) },
//            { Assertions.assertEquals(s1.timeSlot.startDate, s2.timeSlot.startDate) },
//            { Assertions.assertEquals(s1.timeSlot.endDate, s2.timeSlot.endDate) }
        )
    }
}
