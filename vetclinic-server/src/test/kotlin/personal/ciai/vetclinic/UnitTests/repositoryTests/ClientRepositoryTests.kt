package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.repository.ClientRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ClientRepositoryTests {

    @Autowired
    lateinit var clients: ClientRepository

    @Test
    @Transactional
    fun `basic test on findAll`() {
        Assertions.assertTrue(clients.findAll().toList().isNotEmpty())
    }

    @Test
    @Transactional
    fun `test save and delete`() {
        val fakeClient = Client(
            0,
            "ex@gmail.com",
            "Fernando",
            65432,
            "F123",
            "password",
            "Rua da Laranja"
        )

        val client = clients.save(fakeClient)

        assertEquals(client, fakeClient)

        val savedPet = clients.findById(client.id).get()

        assertEquals(fakeClient, savedPet)

        clients.delete(client)

        assertFalse(clients.existsById(savedPet.id))
    }
}
