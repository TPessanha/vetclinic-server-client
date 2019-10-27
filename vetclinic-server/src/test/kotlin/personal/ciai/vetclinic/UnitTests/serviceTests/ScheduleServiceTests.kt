package personal.ciai.vetclinic.UnitTests.serviceTests

import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyListOf
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.repository.ScheduleRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.service.ScheduleService
import personal.ciai.vetclinic.utils.ScheduleUtils.`list of schedules`
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`
import personal.ciai.vetclinic.utils.ScheduleUtils.assertScheduleEquals
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ScheduleServiceTests {

    @Autowired
    lateinit var `schedule service`: ScheduleService

    @MockBean
    lateinit var `veterinarian repository`: VeterinarianRepository

    @MockBean
    lateinit var `schedule repository`: ScheduleRepository

    @Test
    fun `Test - Get One Schedule By Id`() {
        `when`(`schedule repository`.findById(1)).thenReturn(Optional.of(`schedule 1`))

        assertEquals(`schedule service`.getOneScheduleById(1), `schedule 1`)
    }

    @Test
    fun `Test - Get One Schedule By Id (Not Found)`() {
        `when`(`schedule repository`.findById(3)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            `schedule service`.getOneScheduleById(3)
        }
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id`() {
        `when`(`schedule repository`.findByVetId(1)).thenReturn(`list of schedules`)

        assertEquals(`schedule service`.getVeterinarianSchedule(1), `list of schedules`.map { it.toDTO() })
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id (Veterinarian Not Found)`() {
        `when`(`schedule repository`.findByVetId(3)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            `schedule service`.getVeterinarianSchedule(3)
        }
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id And Start at Date Time (Not Found)`() {
        `when`(`schedule repository`.findByVetIdAndYearAndMonth(1, 10, 2019)).thenThrow(
            NotFoundException("not found")
        )

        assertThrows(NotFoundException::class.java) {
            `schedule service`.getScheduleEntityByVetIdAndMonth(1, 10, 2019)
        }
    }

    @Test
    fun `Test - Update Schedule`() {
        `when`(`schedule repository`.save(any(Schedule::class.java)))
            .then {
                val schedule: Schedule = it.getArgument(0)
                assertScheduleEquals(schedule, `schedule 1`)
                schedule
            }

        `when`(`schedule repository`.findById(1)).thenReturn(Optional.of(`schedule 1`))

        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        `schedule service`.setVetSchedule(`schedule 1`.toDTO())
        assert(true)
    }

    @Test
    fun `Test -  Add Schedule for a Month (FAILED)`() {

        `when`(`schedule repository`.saveAll(anyListOf(Schedule::class.java)))
            .then {
                val schedule: List<Schedule> = it.getArgument(0)
                assertEquals(schedule.size, `schedule 1`)
                schedule
            }
        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        `schedule service`.saveSchedule(`schedule 1`)
    }
}
