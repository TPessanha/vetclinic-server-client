package personal.ciai.vetclinic.UnitTests.serviceTests

import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.TestUtils.appointmentExample1
import personal.ciai.vetclinic.TestUtils.appointmentList
import personal.ciai.vetclinic.TestUtils.assertAppointmentEquals
import personal.ciai.vetclinic.TestUtils.clientExample
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.PetRepository
import personal.ciai.vetclinic.service.AppointmentService
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AppointmentServiceTests {

    @Autowired
    lateinit var appointmentService: AppointmentService

    @Autowired
    lateinit var petService: PetService

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
    fun `test cache on getPetAppointments()`() {
        val fakePet = Pet(1, "cat", 3, clientExample)
        val fakeApp = Appointment(1, TimeSlot(532, 3253), fakePet, clientExample)

        fakePet.appointments.add(fakeApp)

        `when`(petRepository.findByIdWithAppointments(anyInt())).thenReturn(Optional.of(fakePet))

        val result1 = appointmentService.getPetAppointments(1)
        verify(petRepository, times(1)).findByIdWithAppointments(1)

        val result2 = appointmentService.getPetAppointments(1)
        verify(petRepository, times(1)).findByIdWithAppointments(1)

        assertEquals(result1, result2)
    }

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val appointmentDTO = appointmentExample1.toDTO()
        assertAppointmentDTO(appointmentDTO)
    }

    private fun assertAppointmentDTO(appointment: AppointmentDTO) {
        assertAll("Is appointmentDTO the same?",
            { assertEquals(appointmentExample1.id, appointment.id) },
            { assertEquals(appointmentExample1.timeSlot.startDate.time, appointment.startTime) },
            { assertEquals(appointmentExample1.timeSlot.endDate.time, appointment.endTime) },
            { assertEquals(appointmentExample1.pet.id, appointment.pet) },
            { assertEquals(appointmentExample1.description, appointment.description) }
        )
    }
}
