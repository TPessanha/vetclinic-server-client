package personal.ciai.vetclinic.UnitTests.serviceTests

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ScheduleServiceTests {

//    @Autowired
//    lateinit var `schedules service`: ScheduleService
//
//    @MockBean
//    lateinit var `veterinarian repository`: VeterinarianRepository
//
//    @MockBean
//    lateinit var `schedules repository`: ScheduleRepository
//
//    @Test
//    fun `Test - Get One Schedule By Id`() {
//        `when`(`schedules repository`.findById(1)).thenReturn(Optional.of(`schedule 1`))
//
//        assertEquals(`schedules service`.getOneScheduleById(1), `schedule 1`.toDTO())
//    }
//
//    @Test
//    fun `Test - Get One Schedule By Id (Not Found)`() {
//        `when`(`schedules repository`.findById(3)).thenThrow(NotFoundException("not found"))
//
//        assertThrows(NotFoundException::class.java) {
//            `schedules service`.getOneScheduleById(3)
//        }
//    }
//
//    @Test
//    fun `Test - Get All Schedule By Veterinarian Id`() {
//        `when`(`schedules repository`.findByVetId(1)).thenReturn(Optional.of(`list of schedules`))
//
//        assertEquals(`schedules service`.getVeterinarianSchedules(1), `list of schedules`.map { it.toDTO() })
//    }
//
//    @Test
//    fun `Test - Get All Schedule By Veterinarian Id (Veterinarian Not Found)`() {
//        `when`(`schedules repository`.findByVetId(3)).thenThrow(NotFoundException("not found"))
//
//        assertThrows(NotFoundException::class.java) {
//            `schedules service`.getVeterinarianSchedules(3)
//        }
//    }

//    @Test
//    fun `Test - Get All Schedule By Veterinarian Id And Start at Date Time`() {
//        `when`(`schedules repository`.getVeterinarianAndStartDateIsEqual(1, `start at time`)).thenReturn(
//            Optional.of(`schedule 1`)
//        )
//        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))
//
//        assertEquals(
//            `schedules service`.getScheduleByVeterinarianAndStartTime(1, `start at time`),
//            `schedule 1`.toDTO()
//        )
//    }

//    @Test
//    fun `Test - Get All Schedule By Veterinarian Id And Start at Date Time (Not Found)`() {
//        `when`(`schedules repository`.getVeterinarianAndStartDateIsEqual(1, `start at time`)).thenThrow(
//            NotFoundException("not found")
//        )
//
//        assertThrows(NotFoundException::class.java) {
//            `schedules service`.getScheduleByVeterinarianAndStartTime(1, `start at time`)
//        }
//    }

//    @Test
//    fun `Test - Get All Schedule By Veterinarian Id And Date Greater than Date Time`() {
//        val `generated list of Schedules` = ScheduleUtils.generateMonth(`veterinarian 1`)
//
//        `when`(
//            `schedules repository`.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
//                1, `start at time`
//            )
//        ).thenReturn(`generated list of Schedules`)
//
//        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))
//
//        assertEquals(
//            `schedules service`.getVeterinarianSchedules(1, `start at time`),
//            `generated list of Schedules`.map { it.toDTO() }
//        )
//    }

//    @Test
//    fun `Test - Update Schedule`() {
//        `when`(`schedules repository`.save(any(Schedules::class.java)))
//            .then {
//                val schedule: Schedules = it.getArgument(0)
//                assertScheduleEquals(schedule, `schedule 1`)
//                schedule
//            }
//
//        `when`(`schedules repository`.findById(1)).thenReturn(Optional.of(`schedule 1`))
//
//        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))
//
//        `schedules service`.update(`schedule 1`.toDTO())
//        assert(true)
//    }

//    @Test
//    fun `Test - Update Schedule Failed`() {
//        val schedule = Schedules(
//            id = 1,
//            timeSlot =
//            TimeSlot(
//                LocalDateTime.now().minusMonths(1).toEpochSecond(ZoneOffset.UTC).toInt(),
//                LocalDateTime.now().minusMonths(1).plusHours(1).toEpochSecond(ZoneOffset.UTC).toInt()
//            ),
//            veterinarian = `veterinarian 1`,
//            status = ScheduleStatus.Available
//        )
//        `when`(`schedules repository`.save(any(Schedules::class.java)))
//            .then {
//                val schedul: Schedules = it.getArgument(0)
//                assertScheduleEquals(schedul, schedule)
//                schedul
//            }
//
//        `when`(`schedules repository`.findById(1)).thenReturn(Optional.of(schedule))
//        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))
//
//        assertThrows(PreconditionFailedException::class.java) {
//            `schedules service`.update(schedule.toDTO())
//        }
//    }

//    @Test
//    fun `Test -  Add Schedule for a Month`() {
//
//        val `generated list of Schedules` = ScheduleUtils.generateMonth(`veterinarian 1`)
//
//        `when`(`schedules repository`.saveAll(anyListOf(Schedules::class.java)))
//            .then {
//                val schedule: List<Schedules> = it.getArgument(0)
//                assertEquals(schedule.size, `generated list of Schedules`.size)
//                schedule
//            }
//        `when`(`veterinarian repository`.findById(1)).thenReturn(Optional.of(`veterinarian 1`))
//
//        `schedules service`.addMonthlySchedule(
//            `generated list of Schedules`.map { it.toDTO() },
//            `veterinarian 1`.id
//        )
//    }

//    @Test
//    fun `Test function toDTO for the Pet Class`() {
//        val schedule = `schedule 1`.toDTO()
//        assertScheduleToDTO(schedule)
//    }

//    private fun assertScheduleToDTO(schedule: SchedulesDTO) {
//        assertAll("Is Schedule Dto the same?",
//            { assertEquals(`schedule 1`.id, schedule.id) },
//            { assertEquals(`schedule 1`.timeSlot.startDate, schedule.startTime) },
//            { assertEquals(`schedule 1`.timeSlot.endDate, schedule.endTime) },
//            { assertEquals(`schedule 1`.status, schedule.status) },
//            { assertEquals(`schedule 1`.veterinarian.id, schedule.vetId) }
//        )
//    }
}
