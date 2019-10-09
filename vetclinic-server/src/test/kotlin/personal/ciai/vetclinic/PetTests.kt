package personal.ciai.vetclinic

import java.net.URI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Pet

/**
 * Description:
 */
class PetTests {

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val pet = buildPet()
        val petDTO = pet.toDTO()
        assertPetDTO(petDTO)
    }

    private fun buildPet(): Pet {
        return Pet(
            2,
            "Duke",
            5,
            notes = "Pretty dog",
            physicalDescription = "Large dog",
            medicalRecord = "medical record",
            photo = URI.create("asdasd")
        )
    }

    private fun assertPetDTO(pet: PetDTO) {
        assertEquals(2, pet.id)
        assertEquals("Duke", pet.species)
        assertEquals(5, pet.age)
        assertEquals("Pretty dog", pet.notes)
        assertEquals("Large dog", pet.physicalDescription)
        assertEquals("medical record", pet.medicalRecord)
    }
}
