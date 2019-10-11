package personal.ciai.vetclinic.IntegrationTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.dogExample
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class PetTests {
    @Autowired
    lateinit var petService: PetService

    @Test
    fun `test on add a new pet()`() {
        assertTrue(petService.getAllPets().size == 0)
        petService.savePet(dogExample.toDTO())
        assertTrue(petService.getAllPets().size == 1)

        val persistentDog = petService.getPetEntityById(1)

        assertNotEquals(dogExample.id, persistentDog.id)
        assertEquals(dogExample.species, persistentDog.species)
        assertEquals(dogExample.medicalRecord, persistentDog.medicalRecord)
        assertEquals(dogExample.physicalDescription, persistentDog.physicalDescription)
        assertEquals(dogExample.notes, persistentDog.notes)
        assertEquals(dogExample.age, persistentDog.age)
//        assertEquals(toAdd.appointments, persistentDog.appointments)

        petService.deletePet(1)
    }
}
