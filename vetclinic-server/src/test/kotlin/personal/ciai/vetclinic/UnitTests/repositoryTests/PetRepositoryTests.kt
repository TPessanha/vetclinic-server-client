package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
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
        val fakePet = Pet(0, "Actually a cat", 3)

        val pet = pets.save(fakePet)

        assertEquals(pet, fakePet)

        val savedPet = pets.findById(pet.id).get()

        assertEquals(fakePet, savedPet)

        pets.delete(pet)

        assertFalse(pets.existsById(savedPet.id))
    }

    @Test
    @Transactional
    fun `test on save and delete 2`() {
        val pet0 = pets.save(dogExample)
        assertPetEquals(pet0, dogExample)

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
