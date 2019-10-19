package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.TestUtils.assertPetEquals
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.TestUtils.pigExample
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
class PetRepositoryTests {
    @Autowired
    lateinit var pets: PetRepository

    @Test
    @Transactional
    fun `basic test on findAll`() {
        assertEquals(pets.findAll().toList(), emptyList<Pet>())
    }

    @Test
    @Transactional
    fun `test save and delete`() {
        val pet = pets.save(dogExample)
        assertNotEquals(dogExample.id, pet.id) // the id is different because it is generated by Spring
        assertEquals(dogExample.species, pet.species)

        val savedPet = pets.findById(pet.id).get()

        assertPetEquals(dogExample, savedPet)

        pets.delete(pet)

        assertFalse(pets.existsById(savedPet.id))
    }

    @Test
    @Transactional
    fun `another test on save and delete`() {
        val pet0 = pets.save(dogExample)
        assertNotEquals(pet0.id, dogExample.id) // the id is different because it is generated by Spring
        assertEquals(pet0.species, dogExample.species)

        assertEquals(pets.findAll().toList(), listOf(pet0))

        val pet1 = pets.save(pigExample)
        assertPetEquals(pet1, pigExample)

        assertEquals(pets.findAll().toList(), listOf(pet0, pet1))

        pets.delete(pet0)

        assertTrue(pets.findAll().toList().size == 1)

        pets.delete(pet1)

        assertTrue(pets.findAll().toList().isEmpty())
    }
}
