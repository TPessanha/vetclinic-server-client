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
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.ClientRepository
import personal.ciai.vetclinic.repository.PetRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
class PetRepositoryTests {
    @Autowired
    lateinit var pets: PetRepository

    @Autowired
    lateinit var clientRepository: ClientRepository

    @Test
    @Transactional
    fun `basic test on findAll`() {
        assertEquals(pets.findAll().toList().size, 2)
    }

    @Test
    @Transactional
    fun `test save and delete`() {
        val fakeClient = Client(
            0,
            "sdg@gmail.com",
            "Rui",
            41235,
            "Rui123",
            "password",
            "Rua asdasd asdasd"
        )
        val fakePet = Pet(0, "Actually a cat", 3, owner = fakeClient)

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
        val fakeClient = Client(
            0,
            "sdg@gmail.com",
            "Rui",
            41235,
            "Rui123",
            "password",
            "Rua asdasd asdasd"
        )
        clientRepository.save(fakeClient)

        val previousList = pets.findAll().toMutableList()

        val fakePet = Pet(0, "Actually a cat", 3, owner = fakeClient)
        val pet0 = pets.save(fakePet)
        assertPetEquals(pet0, fakePet)

        previousList.add(pet0)
        assertEquals(pets.findAll().toList(), previousList)

        val fakePet2 = Pet(0, "Actually a pig", 2, owner = fakeClient)
        val pet1 = pets.save(fakePet2)
        assertPetEquals(pet1, fakePet2)

        previousList.add(pet1)
        assertEquals(pets.findAll().toList(), previousList)

        pets.delete(pet0)

        assertTrue(pets.findAll().toList().size == 3)

        pets.delete(pet1)

        assertTrue(pets.findAll().toList().size == 2)
    }
}
