package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vet1
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vet2
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.VeterinarianRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class VeterinarianRepositoryTests {
    @Autowired
    lateinit var vet: VeterinarianRepository

    @Test
    fun `basic test on findAll`() {
        assertEquals(vet.findAll().toList(), emptyList<Pet>())
    }

    @Test
    fun `basic test on save and delete`() {
        val vet01 = vet.save(vet1)
        assertNotEquals(vet1.id, vet01.id) // the id is different because it is generated by Spring
        assertEquals(vet1.username, vet01.username)

        val saved = vet.findById(vet01.id).get()

        assertAll("Is vet the same",
            { assertNotEquals(vet1.id, saved.id) },
            { assertEquals(vet1.name, saved.name) },
            { assertEquals(vet1.username, saved.username) }
        )

        vet.delete(vet01)

        assertTrue(vet.findAll().toList().size == 0)
    }

    fun <T> nonNullAny(t: Class<T>): T = Mockito.any(t)

    @Test
    fun `another test on save and delete`() {
//        val vetMock : Pet = mock(Pet::class.java)
//        `when`(vetMock.appointments).thenReturn(emptyList())

        val vet01 = vet.save(vet1)
        assertNotEquals(vet01.id, vet1.id) // the id is different because it is generated by Spring
        assertEquals(vet01.username, vet1.username)
        assertEquals(vet01.email, vet1.email)

        assertEquals(vet.findAll().toList(), listOf(vet01))

        val vet02 = vet.save(vet2)
        assertNotEquals(vet02.id, vet2.id) // the id is different because it is generated by Spring
        assertNotEquals(vet02.email, vet2.email)
        assertNotEquals(vet02.username, vet2.username)

        assertEquals(vet.findAll().toList(), listOf(vet01, vet02))

        vet.delete(vet01)

        assertTrue(vet.findAll().toList().size == 1)

        vet.delete(vet02)

        assertTrue(vet.findAll().toList().size == 0)
    }
}
