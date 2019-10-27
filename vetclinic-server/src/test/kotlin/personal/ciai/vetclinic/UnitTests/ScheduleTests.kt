package personal.ciai.vetclinic.UnitTests

import java.util.BitSet
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.model.Veterinarian

class ScheduleTests {
    @Test
    fun `Test checkConflict()`() {
        val mockedVet = Mockito.mock(Veterinarian::class.java)

        val blocks = BitSet(720)

        blocks[0] = true
        blocks[1] = true
        blocks[2] = true
        blocks[3] = true
        blocks[4] = true // 5
        blocks[5] = false // 6
        blocks[6] = false
        blocks[7] = false
        blocks[8] = false
        blocks[9] = false
        blocks[10] = true
        blocks[11] = true
        blocks[12] = false
        blocks[13] = false
        blocks[14] = true
        blocks[15] = true
        blocks[16] = true
        blocks[17] = true
        blocks[18] = false
        blocks[19] = false

        Schedule(0, 10, 2019, mockedVet, blocks.toByteArray(), blocks.toByteArray())
    }

    @Test
    fun `Test checkConflict() (Conflict 12H)`() {
        val mockedVet = Mockito.mock(Veterinarian::class.java)

        val blocks = BitSet(720)

        blocks[0] = true
        blocks[1] = true
        blocks[2] = true
        blocks[3] = true
        blocks[4] = true // 5
        blocks[5] = true // 6
        blocks[6] = true
        blocks[7] = true
        blocks[8] = true
        blocks[9] = true
        blocks[10] = true
        blocks[11] = true
        blocks[12] = true
        blocks[13] = true
        blocks[14] = true
        blocks[15] = true
        blocks[16] = true
        blocks[17] = true
        blocks[18] = false
        blocks[19] = false

        assertThrows(PreconditionFailedException::class.java) {
            Schedule(0, 10, 2019, mockedVet, blocks.toByteArray(), blocks.toByteArray())
        }
    }

    @Test
    fun `Test checkConflict() (Conflict rest)`() {
        val mockedVet = Mockito.mock(Veterinarian::class.java)

        val blocks = BitSet(720)

        blocks[0] = true
        blocks[1] = true
        blocks[2] = true
        blocks[3] = true
        blocks[4] = true // 5
        blocks[5] = true // 6
        blocks[6] = false
        blocks[7] = false
        blocks[8] = false
        blocks[9] = false
        blocks[10] = false
        blocks[11] = false
        blocks[12] = true
        blocks[13] = true
        blocks[14] = true
        blocks[15] = true
        blocks[16] = true
        blocks[17] = true
        blocks[18] = false
        blocks[19] = false

        assertThrows(PreconditionFailedException::class.java) {
            Schedule(0, 10, 2019, mockedVet, blocks.toByteArray(), blocks.toByteArray())
        }
    }

    @Test
    fun `Test checkConflict() (Conflict 160 and 40)`() {
        val mockedVet = Mockito.mock(Veterinarian::class.java)

        val blocks = BitSet.valueOf(LongArray(720) { Long.MAX_VALUE })

        assertThrows(PreconditionFailedException::class.java) {
            Schedule(0, 10, 2019, mockedVet, blocks.toByteArray(), blocks.toByteArray())
        }
    }

    @Test
    fun `Test checkConflict() (Conflict booked in unavailable)`() {
        val mockedVet = Mockito.mock(Veterinarian::class.java)

        val blocks = BitSet(720)
        val booked = BitSet(720)

        booked.set(3)

        assertThrows(ConflictException::class.java) {
            Schedule(0, 10, 2019, mockedVet, blocks.toByteArray(), booked.toByteArray())
        }
    }
}
