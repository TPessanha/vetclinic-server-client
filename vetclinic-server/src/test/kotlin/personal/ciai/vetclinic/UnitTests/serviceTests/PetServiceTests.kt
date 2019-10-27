package personal.ciai.vetclinic.UnitTests.serviceTests

import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.anyInt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.TestUtils.assertPetEquals
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.TestUtils.petList
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository
import personal.ciai.vetclinic.service.PetService

/**
 * Description:
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class PetServiceTests {

    @Autowired
    lateinit var petService: PetService

    @MockBean
    lateinit var repository: PetRepository

    @Test
    fun `basic test on getAll`() {
        `when`(repository.findAllByEnabled(anyBoolean())).thenReturn(petList)

        assertEquals(petList.map { it.toDTO() }, petService.getAllPets())
    }

    @Test
    fun `basic test on getOne`() {
        `when`(repository.findById(1)).thenReturn(Optional.of(dogExample))

        assertEquals(petService.getPetById(1), dogExample.toDTO())
    }

    @Test
    fun `test on getPetById() (Not Found))`() {
        `when`(repository.findById(5)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            petService.getPetById(5)
        }
    }

    @Test
    fun `test on savePet()`() {
        `when`(repository.save(Mockito.any(Pet::class.java)))
            .then {
                val pet: Pet = it.getArgument(0)
                assertPetEquals(pet, dogExample)
                pet
            }

        petService.addPet(dogExample.toDTO().copy(owner = 2))
    }

    @Test
    fun `test on updatePet() (Expectation Failed)`() {
        assertThrows(PreconditionFailedException::class.java) {
            petService.addPet(dogExample.toDTO().copy(id = -1))
        }
        assertThrows(PreconditionFailedException::class.java) {
            petService.addPet(dogExample.toDTO().copy(id = 5))
        }
    }

    @Test
    fun `Test updatePet() (Not Found)`() {
        `when`(repository.existsById(anyInt())).thenReturn(false)

        assertThrows(NotFoundException::class.java) {
            petService.updatePet(dogExample.toDTO(), 5)
        }
    }

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val petDTO = dogExample.toDTO()
        assertPetDTO(petDTO)
    }

    private fun assertPetDTO(pet: PetDTO) {
        assertAll("Is petDTO the same?",
            { assertEquals(dogExample.id, pet.id) },
            { assertEquals(dogExample.species, pet.species) },
            { assertEquals(dogExample.age, pet.age) },
            { assertEquals(dogExample.notes, pet.notes) },
            { assertEquals(dogExample.physicalDescription, pet.physicalDescription) },
            { assertEquals(dogExample.medicalRecord, pet.medicalRecord) }
        )
    }
}
