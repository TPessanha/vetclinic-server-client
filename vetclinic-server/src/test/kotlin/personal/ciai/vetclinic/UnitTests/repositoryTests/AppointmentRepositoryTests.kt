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
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.TestUtils.assertAppointmentEquals
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
    fun `test save Appointment with no Pet (Data Integrity Violation)`() {
        val fakePet = Pet(555, "moon dog", 2)
        val app = Appointment(0, Date(1571414431763), fakePet, "Scheduled for health stuff")

//        `when`(pets.findById(555)).thenReturn(Optional.empty())

        assertThrows(DataIntegrityViolationException::class.java) {
            appointments.save(app)
        }
    }

    @Test
    @Transactional
    fun `test save and delete`() {
        // Add pet and appointment
        val fakePet = Pet(0, "Actually a bunny", 2)
        val fakeApp = Appointment(0, Date(), fakePet, "stuff")
        fakePet.appointments.add(fakeApp)
        val savedPet = pets.save(fakePet)

        // check
        val savedAppointment = appointments.findByPetId(savedPet.id).get(0)
        assertAppointmentEquals(savedAppointment, fakeApp)

        // delete
        fakePet.appointments.remove(fakeApp)
        pets.save(fakePet)

        // check
        assertFalse(appointments.existsById(savedAppointment.id))
        assertTrue(pets.existsById(savedPet.id))
    }
}
