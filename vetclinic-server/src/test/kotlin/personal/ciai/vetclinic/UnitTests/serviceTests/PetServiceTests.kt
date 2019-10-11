package personal.ciai.vetclinic.UnitTests.serviceTests

import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.dogExample
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.petList
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.petListPet
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
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
        `when`(repository.findAll()).thenReturn(petListPet)

        assertEquals(petService.getAllPets().map { it.copy(id = -1) }, petList)
    }

    @Test
    fun `basic test on getOne`() {
        `when`(repository.findById(1)).thenReturn(Optional.of(dogExample))

        assertEquals(petService.getPetById(1), dogExample.toDTO())
    }

    @Test
    fun `test on getPetById() exception`() {
        `when`(repository.findById(5)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            petService.getPetById(5)
        }
    }

    @Test
    fun `test on addNewPet()`() {
        `when`(repository.save(Mockito.any(Pet::class.java)))
            .then {
                val pet: Pet = it.getArgument(0)
                assertEquals(pet.id, (dogExample.id))
                assertEquals(pet.species, (dogExample.species))
                pet
            }

        petService.savePet(dogExample.toDTO())
    }

    @Test
    fun `Test function toDTO for the Pet Class`() {
        val petDTO = dogExample.toDTO()
        assertPetDTO(petDTO)
    }

    private fun assertPetDTO(pet: PetDTO) {
        assertEquals(dogExample.id, pet.id)
        assertEquals(dogExample.species, pet.species)
        assertEquals(dogExample.age, pet.age)
        assertEquals(dogExample.notes, pet.notes)
        assertEquals(dogExample.physicalDescription, pet.physicalDescription)
        assertEquals(dogExample.medicalRecord, pet.medicalRecord)
    }
}
