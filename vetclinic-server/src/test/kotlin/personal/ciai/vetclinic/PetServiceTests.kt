package personal.ciai.vetclinic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.PetService
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.dog

/**
 * Description:
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class PetServiceTests {

    @Autowired
    lateinit var petService: PetService

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val petDTO = dog.toDTO()
        assertPetDTO(petDTO)
    }

    @Test
    fun `test NotFoundException on getPetByID`() {
        Assertions.assertThrows(NotFoundException::class.java) {
            petService.getPetById(5)
        }
    }

    private fun assertPetDTO(pet: PetDTO) {
        assertEquals(dog.id, pet.id)
        assertEquals(dog.species, pet.species)
        assertEquals(dog.age, pet.age)
        assertEquals(dog.notes, pet.notes)
        assertEquals(dog.physicalDescription, pet.physicalDescription)
        assertEquals(dog.medicalRecord, pet.medicalRecord)
    }
}
