package personal.ciai.vetclinic.UnitTests.serviceTests

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyListOf
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.dto.SchedulesDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.repository.SchedulesRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.service.SchedulesService
import personal.ciai.vetclinic.utils.ScheduleUtils
import personal.ciai.vetclinic.utils.ScheduleUtils.`list of schedules`
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`
import personal.ciai.vetclinic.utils.ScheduleUtils.`start at time`
import personal.ciai.vetclinic.utils.ScheduleUtils.assertScheduleEquals
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ScheduleServiceTests {

    @Autowired
    lateinit var `schedules service`: SchedulesService

    @MockBean
    lateinit var `veterinarian repository`: VeterinarianRepository

    @MockBean
    lateinit var `schedules repository`: SchedulesRepository

    @Test
    fun `Test - Get One Schedule By Id`() {
        `when`(`schedules repository`.findById(1)).thenReturn(Optional.of(`schedule 1`))

        assertEquals(`schedules service`.getOneScheduleById(1), `schedule 1`.toDTO())
    }

    @Test
    fun `Test - Get One Schedule By Id (Not Found)`() {
        `when`(`schedules repository`.findById(3)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            `schedules service`.getOneScheduleById(3)
        }
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id`() {
        `when`(`schedules repository`.findAllByVeterinarian(1)).thenReturn(Optional.of(`list of schedules`))

        assertEquals(`schedules service`.getVeterinarianSchedules(1), `list of schedules`.map { it.toDTO() })
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id (Veterinarian Not Found)`() {
        `when`(`schedules repository`.findAllByVeterinarian(3)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            `schedules service`.getVeterinarianSchedules(3)
        }
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id And Start at Date Time`() {
        `when`(`schedules repository`.getVeterinarianAndStartDateIsEqual(1, `start at time`)).thenReturn(
            Optional.of(`schedule 1`)
        )
        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        assertEquals(
            `schedules service`.getScheduleByVeterinarianAndStartTime(1, `start at time`),
            `schedule 1`.toDTO()
        )
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id And Start at Date Time (Not Found)`() {
        `when`(`schedules repository`.getVeterinarianAndStartDateIsEqual(1, `start at time`)).thenThrow(
            NotFoundException("not found")
        )

        assertThrows(NotFoundException::class.java) {
            `schedules service`.getScheduleByVeterinarianAndStartTime(1, `start at time`)
        }
    }

    @Test
    fun `Test - Get All Schedule By Veterinarian Id And Date Greater than Date Time`() {
        val `generated list of Schedules` = ScheduleUtils.generateMonth(`veterinarian 1`)

        `when`(
            `schedules repository`.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
                1, `start at time`
            )
        ).thenReturn(`generated list of Schedules`)

        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        assertEquals(
            `schedules service`.getVeterinarianSchedules(1, `start at time`),
            `generated list of Schedules`.map { it.toDTO() }
        )
    }

    @Test
    fun `Test - Update Schedule`() {
        `when`(`schedules repository`.save(any(Schedules::class.java)))
            .then {
                val schedule: Schedules = it.getArgument(0)
                assertScheduleEquals(schedule, `schedule 1`)
                schedule
            }

        `when`(`schedules repository`.findById(1)).thenReturn(Optional.of(`schedule 1`))

        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        `schedules service`.update(`schedule 1`.toDTO())
        assert(true)
    }

    @Test
    fun `Test - Update Schedule Failed`() {
        val schedule = Schedules(
            id = 1,
            timeSlot =
            TimeSlot(
                LocalDateTime.now().minusMonths(1).toEpochSecond(ZoneOffset.UTC).toInt(),
                LocalDateTime.now().minusMonths(1).plusHours(1).toEpochSecond(ZoneOffset.UTC).toInt()
            ),
            veterinarian = `veterinarian 1`,
            status = ScheduleStatus.Available
        )
        `when`(`schedules repository`.save(any(Schedules::class.java)))
            .then {
                val schedul: Schedules = it.getArgument(0)
                assertScheduleEquals(schedul, schedule)
                schedul
            }

        `when`(`schedules repository`.findById(1)).thenReturn(Optional.of(schedule))
        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        assertThrows(PreconditionFailedException::class.java) {
            `schedules service`.update(schedule.toDTO())
        }
    }

    @Test
    fun `Test -  Add Schedule for a Month`() {

        val `generated list of Schedules` = ScheduleUtils.generateMonth(`veterinarian 1`)

        `when`(`schedules repository`.saveAll(anyListOf(Schedules::class.java)))
            .then {
                val schedule: List<Schedules> = it.getArgument(0)
                assertEquals(schedule.size, `generated list of Schedules`.size)
                schedule
            }
        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))

        `schedules service`.addMonthlySchedule(
            `generated list of Schedules`.map { it.toDTO() },
            `veterinarian 1`.id
        )
    }

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val schedule = `schedule 1`.toDTO()
        assertScheduleToDTO(schedule)
    }

    private fun assertScheduleToDTO(schedule: SchedulesDTO) {
        assertAll("Is Schedule Dto the same?",
            { assertEquals(`schedule 1`.id, schedule.id) },
            { assertEquals(`schedule 1`.timeSlot.startDate, schedule.startTime) },
            { assertEquals(`schedule 1`.timeSlot.endDate, schedule.endTime) },
            { assertEquals(`schedule 1`.status, schedule.status) },
            { assertEquals(`schedule 1`.veterinarian.id, schedule.vetId) }
        )
    }
}
