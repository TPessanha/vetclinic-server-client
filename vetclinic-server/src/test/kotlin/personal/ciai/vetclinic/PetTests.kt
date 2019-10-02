package personal.ciai.vetclinic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Pet

/**
 * Description:
 */
class PetTests {

    @Test
    @DisplayName("Test function toDTO in the Pet Class")
    fun testPetToDTO() {
        val pet = buildPet()
        val petDTO = pet.toDTO()
        assertPetDTO(petDTO)
    }

    private fun buildPet(): Pet {
        return Pet(
            2,
            "Duke",
            "Doggi"
        )
    }

    private fun assertPetDTO(pet: PetDTO) {
        assertEquals(2, pet.id)
        assertEquals("Duke", pet.name)
        assertEquals("Doggi", pet.species)
    }
}
