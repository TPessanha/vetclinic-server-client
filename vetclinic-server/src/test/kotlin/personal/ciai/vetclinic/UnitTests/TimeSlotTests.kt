package personal.ciai.vetclinic.UnitTests

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import personal.ciai.vetclinic.model.TimeSlot

class TimeSlotTests {
    @Test
    fun `Test checkConflict()`() {
        val schedule1 = TimeSlot(1000, 2000)
        // False
        val schedule2 = TimeSlot(1200, 1800)
        val schedule3 = TimeSlot(500, 1500)
        val schedule4 = TimeSlot(1500, 2500)
        val schedule5 = TimeSlot(500, 2500)
        // True
        val schedule6 = TimeSlot(500, 800)
        val schedule7 = TimeSlot(2500, 3000)

        assertAll("Check conflicts",
            { assertFalse(schedule1.checkConflict(schedule2)) },
            { assertFalse(schedule1.checkConflict(schedule3)) },
            { assertFalse(schedule1.checkConflict(schedule4)) },
            { assertFalse(schedule1.checkConflict(schedule5)) }
        )

        assertAll("Check non conflicts",
            { assertTrue(schedule1.checkConflict(schedule6)) },
            { assertTrue(schedule1.checkConflict(schedule7)) }
        )
    }
}
