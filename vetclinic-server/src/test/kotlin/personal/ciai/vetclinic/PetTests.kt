package personal.ciai.vetclinic

import org.junit.Test
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Pet

/**
 * Description:
 */
class PetTests {

    @Test
    fun `maps User to UserResponse using extension function`() {
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
        assert(2 == pet.id)
        assert("Duke".equals(pet.name))
        assert("Doggi".equals(pet.species))
    }
}
