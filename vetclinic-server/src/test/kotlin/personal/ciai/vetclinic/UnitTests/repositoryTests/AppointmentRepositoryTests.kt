package personal.ciai.vetclinic.UnitTests.repositoryTests

import java.net.URI
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
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.ClientRepository
import personal.ciai.vetclinic.repository.PetRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AppointmentRepositoryTests {
    @Autowired
    lateinit var appointments: AppointmentRepository

    @Autowired
    lateinit var clientRepository: ClientRepository

    @Autowired
    lateinit var veterinarianRepository: VeterinarianRepository

    @Autowired
    lateinit var pets: PetRepository

    @Test
    @Transactional
    fun `basic test on findAll`() {
        assertEquals(appointments.findAll().toList().size, 2)
    }

    @Test
    @Transactional
    fun `test save Appointment with no Pet (Data Integrity Violation)`() {
        val fakeClient = Client(0, "gaer@gmail.com", "Pedro", 412532, "Pedro123", "password", "Rua Pedro da cenas")
        val fakeVet =
            Veterinarian(
                0,
                "vetmail@das",
                "veterio",
                52345235,
                "vet123",
                "secret",
                "Rua dos vets",
                URI("asdf"),
                true,
                arrayListOf(),
                12,
                arrayListOf()
            )
        val fakePet = Pet(555, "moon dog", 2, owner = fakeClient)

        val app =
            Appointment(0, 10, 2019, TimeSlot(5, 10), fakeVet, fakePet, fakeClient, "Serious description")

//        `when`(pets.findById(555)).thenReturn(Optional.empty())

        assertThrows(DataIntegrityViolationException::class.java) {
            appointments.save(app)
        }
    }

    @Test
    @Transactional
    fun `test save and delete`() {
        // Add pet and appointment
        val fakeClient = Client(0, "gaer@gmail.com", "Pedro", 412532, "Pedro123", "password", "Rua Pedro da cenas")
        val fakeVet =
            Veterinarian(
                0,
                "vetmail@das",
                "veterio",
                52345235,
                "vet123",
                "secret",
                "Rua dos vets",
                URI("asdf"),
                true,
                arrayListOf(),
                12,
                arrayListOf()
            )
        val fakePet = Pet(0, "Actually a bunny", 2, owner = fakeClient)
        val fakeApp =
            Appointment(0, 10, 2019, TimeSlot(5, 10), fakeVet, pet = fakePet, client = fakeClient)

        fakeClient.appointments.add(fakeApp)
        clientRepository.save(fakeClient)
        veterinarianRepository.save(fakeVet)
        val savedPet = pets.save(fakePet)
        appointments.save(fakeApp)

        // check
        val savedAppointment = appointments.findByPetId(savedPet.id).get(0)
        assertAppointmentEquals(savedAppointment, fakeApp)

        // delete
        fakeClient.appointments.remove(fakeApp)
        clientRepository.save(fakeClient)

        fakePet.appointments.remove(fakeApp)
        pets.save(fakePet)

        appointments.deleteById(savedAppointment.id)
        // check
        assertFalse(appointments.existsById(savedAppointment.id))
        assertTrue(pets.existsById(savedPet.id))
    }
}
