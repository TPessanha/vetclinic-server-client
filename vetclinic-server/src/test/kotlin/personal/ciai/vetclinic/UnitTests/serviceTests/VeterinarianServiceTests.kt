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
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vet1
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vetList
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.service.VeterinarianService

/**
 * Description:
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class VeterinarianServiceTests {

    @Autowired
    lateinit var vetsService: VeterinarianService

    @MockBean
    lateinit var repository: VeterinarianRepository

    @Test
    fun `basic test on get All`() {
        `when`(repository.findAllByEnabled(true)).thenReturn(vetList)

        assertEquals(vetsService.getAllVeterinarian(), vetList.map { it.toDTO() })
    }

    @Test
    fun `basic test on get only One`() {
        `when`(repository.findById(1)).thenReturn(Optional.of(vet1))

        assertEquals(vetsService.getVeterinarianById(1), vet1.toDTO())
    }

    @Test
    fun `test on getVeterinarianById() exception`() {
        `when`(repository.findById(4)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            vetsService.getVeterinarianById(4)
        }
    }

    @Test
    fun `test on add new Veterinarian`() {
        `when`(repository.save(Mockito.any(Veterinarian::class.java)))
            .then {
                val vets: Veterinarian = it.getArgument(0)
                assertEquals(vets.id, vet1.id)
                assertEquals(vets.name, vet1.name)
                assertEquals(vets.email, vet1.email)
                vets
            }

        vetsService.save(vet1.toDTO())
    }

    @Test
    fun `Test function toDTO for the Veterinarian Class`() {
        val vetsDTO = vet1.toDTO()
        `assert VeterinarianDTO`(vetsDTO)
    }

    private fun `assert VeterinarianDTO`(vets: VeterinarianDTO) {
        assertEquals(vets.id, (vet1.id))
        assertEquals(vets.name, (vet1.name))
        assertEquals(vets.email, (vet1.email))
        assertEquals(vets.address, (vet1.address))
        assertEquals(vets.phoneNumber, (vet1.phoneNumber))
        assertEquals(vets.username, (vet1.username))
        assertEquals(vets.password, "")
    }
}
