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
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.admin1
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.listAdmin
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Administrative
import personal.ciai.vetclinic.repository.AdministrativeRepository
import personal.ciai.vetclinic.service.AdministrativeService

/**
 * Description:
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class AdministrativeServiceTests {

    @Autowired
    lateinit var adminService: AdministrativeService

    @MockBean
    lateinit var repository: AdministrativeRepository

    @Test
    fun `basic test on get All`() {
        `when`(repository.findAll()).thenReturn(listAdmin)

        assertEquals(adminService.getAllAdministrative(), listAdmin.map { it.toDTO() })
    }

    @Test
    fun `basic test on get only One`() {
        `when`(repository.findById(1)).thenReturn(Optional.of(admin1))

        assertEquals(adminService.getAdministrativeById(1), admin1.toDTO())
    }

    @Test
    fun `test on getAdministrativeById() exception`() {
        `when`(repository.findById(4)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            adminService.getAdministrativeById(4)
        }
    }

    @Test
    fun `test on add new Administrative`() {
        `when`(repository.save(Mockito.any(Administrative::class.java)))
            .then {
                val admin: Administrative = it.getArgument(0)
                assertEquals(admin.id, admin1.id)
                assertEquals(admin.name, admin1.name)
                assertEquals(admin.email, admin1.email)
                admin
            }

        adminService.save(admin1.toDTO())
    }

    @Test
    fun `Test function toDTO for the Administrative Class`() {
        val adminDTO = admin1.toDTO()
        `assert AdministrativeDTO`(adminDTO)
    }

    private fun `assert AdministrativeDTO`(admin: AdministrativeDTO) {
        assertEquals(admin.id, (admin1.id))
        assertEquals(admin.name, (admin1.name))
        assertEquals(admin.email, (admin1.email))
        assertEquals(admin.address, (admin1.address))
        assertEquals(admin.phoneNumber, (admin1.phoneNumber))
        assertEquals(admin.photo, admin1.photo.toString())
        assertEquals(admin.username, (admin1.username))
        assertEquals(admin.password, "")
    }
}
