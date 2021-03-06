package personal.ciai.vetclinic.UnitTests.repositoryTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class VeterinarianRepositoryTests {
    @Autowired
    lateinit var vet: VeterinarianRepository

    @Test
    fun `basic test on findAll`() {
        assertEquals(vet.findAll().toList().size, 1)
    }

    @Test
    fun `basic test on save and delete`() {
        val nVet = listOf(vet.findAll())

        val vet01 = vet.save(`veterinarian 1`)
        assertNotEquals(`veterinarian 1`.id, vet01.id) // the id is different because it is generated by Spring
        assertEquals(`veterinarian 1`.username, vet01.username)

        val saved = vet.findById(vet01.id).get()

        assertAll("Is vet the same",
            { assertNotEquals(`veterinarian 1`.id, saved.id) },
            { assertEquals(`veterinarian 1`.name, saved.name) },
            { assertEquals(`veterinarian 1`.username, saved.username) }
        )
        assertTrue(vet.findAll().toList().size == nVet.size + 1)
        vet.delete(vet01)

        assertTrue(vet.findAll().toList().filter { it.enabled }.size == nVet.size)
    }

//    @Test
//    fun `another test on save and delete`() {
//        var nVet = vet.findAll().toList()
//
//        val vet01 = vet.save(`veterinarian 2`)
//        assertNotEquals(vet01.id, `veterinarian 2`.id) // the id is different because it is generated by Spring
//        assertEquals(vet01.username, `veterinarian 2`.username)
//        assertEquals(vet01.email, `veterinarian 2`.email)
//
//        nVet.add(vet01)
//        assertEquals(vet.findAll().toList(), listOf(nVet, vet01))
//
//        val vet02 = vet.save(`veterinarian 1`)
//        assertNotEquals(vet02.id, `veterinarian 1`.id) // the id is different because it is generated by Spring
//        assertEquals(vet02.email, `veterinarian 1`.email)
//        assertEquals(vet02.username, `veterinarian 1`.username)
//
//        nVet.add(vet02)
//        assertEquals(vet.findAll().toList(), nVet)
//
//        nVet.remove(vet01)
//        vet.delete(vet01)
//
//        assertTrue(vet.findAll().toList().filter { it.enabled }.size == nVet.size)
//
//        vet.delete(vet02)
//        nVet.remove(vet02)
//        assertTrue(vet.findAll().toList().filter { it.enabled }.size == nVet.size)
//    }
}
