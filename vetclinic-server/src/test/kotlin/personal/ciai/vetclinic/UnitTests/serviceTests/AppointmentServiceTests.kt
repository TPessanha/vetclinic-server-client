package personal.ciai.vetclinic.UnitTests.serviceTests

import java.util.Date
import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.TestUtils.appointmentExample1
import personal.ciai.vetclinic.TestUtils.appointmentList
import personal.ciai.vetclinic.TestUtils.assertAppointmentEquals
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.PetRepository
import personal.ciai.vetclinic.service.AppointmentService

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AppointmentServiceTests {

    @Autowired
    lateinit var appointmentService: AppointmentService

    @MockBean
    lateinit var repository: AppointmentRepository

    @MockBean
    lateinit var petRepository: PetRepository

    @Test
    fun `basic test on getAll`() {
        `when`(repository.findAll()).thenReturn(appointmentList)

        assertEquals(appointmentService.getAllAppointments(), appointmentList.map { it.toDTO() })
    }

    @Test
    fun `basic test on getOne`() {
        `when`(repository.findById(1)).thenReturn(Optional.of(appointmentExample1))

        assertEquals(appointmentService.getAppointmentById(1), appointmentExample1.toDTO())
    }

    @Test
    fun `test on getPetById() (Not Found))`() {
        `when`(repository.findById(5)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            appointmentService.getAppointmentById(5)
        }
    }

    @Test
    fun `test on addAppointment() (Pet Not Found)`() {
        `when`(repository.save(any(Appointment::class.java)))
            .then {
                val appointment: Appointment = it.getArgument(0)
                assertAppointmentEquals(appointment, appointmentExample1)
                appointment
            }

        `when`(petRepository.findById(anyInt())).thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) {
            appointmentService.addAppointment(appointmentExample1.toDTO())
        }
    }

    @Test
    fun `test on addAppointment()`() {
        `when`(repository.save(any(Appointment::class.java)))
            .then {
                val appointment: Appointment = it.getArgument(0)
                assertAppointmentEquals(appointment, appointmentExample1)
                appointment
            }

        `when`(petRepository.findById(anyInt())).thenReturn(Optional.of(dogExample))

        appointmentService.addAppointment(appointmentExample1.toDTO().copy(id = 0, client = 1))
    }

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val appointmentDTO = appointmentExample1.toDTO()
        assertAppointmentDTO(appointmentDTO)
    }

    private fun assertAppointmentDTO(appointment: AppointmentDTO) {
        assertAll("Is appointmentDTO the same?",
            { assertEquals(appointmentExample1.id, appointment.id) },
            { assertEquals(appointmentExample1.date, Date(appointment.date)) },
            { assertEquals(appointmentExample1.pet.id, appointment.pet) },
            { assertEquals(appointmentExample1.description, appointment.description) }
        )
    }
}
