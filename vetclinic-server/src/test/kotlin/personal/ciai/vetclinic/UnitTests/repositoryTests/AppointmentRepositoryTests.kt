package personal.ciai.vetclinic.UnitTests.repositoryTests

import java.util.Date
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.TestUtils.appointmentExample1
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.PetRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AppointmentRepositoryTests {
    @Autowired
    lateinit var appointments: AppointmentRepository
    @Autowired
    lateinit var pets: PetRepository

    @Test
    @Transactional
    fun `basic test on findAll`() {
        assertEquals(appointments.findAll().toList(), emptyList<Appointment>())
    }

    @Test
    @Transactional
    fun `test save Appointment with no Pet (EntityNotFound)`() {
        assertThrows(JpaObjectRetrievalFailureException::class.java) {
            appointments.save(appointmentExample1)
        }
    }

    @Test
    @Transactional
    fun `test save and delete`() {
        // Add pet and appointment
        var tmpPet = Pet(1, "Actually a bunny", 2)
        var tmpApp = Appointment(-1, Date(), tmpPet, "stuff")
        tmpPet.appointments.add(tmpApp)
        val savedPet = pets.save(tmpPet)

        // check
        val appointment = appointments.findByPetId(savedPet.id).get(0)
        val savedAppointment = appointments.findById(appointment.id).get()
        assertEquals(appointment, savedAppointment)

        // delete
        savedPet.appointments.remove(appointment)
        pets.save(savedPet)

        // check
        assertFalse(appointments.existsById(savedAppointment.id))
        assertTrue(pets.existsById(savedPet.id))
    }
}
