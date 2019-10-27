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
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.admin1
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.admin3
import personal.ciai.vetclinic.repository.AdministratorRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class AdministratorRepositoryTests {
    @Autowired
    lateinit var admin: AdministratorRepository

    @Test
    fun `basic test on findAll`() {
        assertEquals(admin.findAll().toList().size, 1)
    }

    @Test
    fun `basic test on save and delete`() {
        val admin01 = admin.save(admin1)
        assertNotEquals(admin1.id, admin01.id)
        assertEquals(admin1.username, admin01.username)

        val saved = admin.findById(admin01.id).get()

        assertAll("Is admin the same",
            { assertNotEquals(admin1.id, saved.id) },
            { assertEquals(admin1.name, saved.name) },
            { assertEquals(admin1.username, saved.username) }
        )

        admin.delete(admin01)

        assertTrue(admin.findAll().toList().size >= 1)
    }

    fun <T> nonNullAny(t: Class<T>): T = Mockito.any(t)

    @Test
    fun `another test on save and delete`() {
        var nAdmin = admin.findAll()

        val admin01 = admin.save(admin1)
        assertNotEquals(admin01.id, admin1.id)
        assertEquals(admin01.username, admin1.username)
        assertEquals(admin01.email, admin1.email)

        nAdmin.add(admin01)
        assertEquals(admin.findAll().toList(), nAdmin)

        val admin03 = admin.save(admin3)
        assertNotEquals(admin03.id, admin3.id)
        assertEquals(admin03.email, admin3.email)
        assertEquals(admin03.username, admin3.username)

        nAdmin.add(admin03)
        assertEquals(admin.findAll().toList(), nAdmin)

        admin.delete(admin01)

        assertTrue(admin.findAll().toList().size + 1 == nAdmin.size)

        nAdmin.add(admin03)
        admin.delete(admin03)

        assertTrue(admin.findAll().toList().size == nAdmin.size)
    }
}
