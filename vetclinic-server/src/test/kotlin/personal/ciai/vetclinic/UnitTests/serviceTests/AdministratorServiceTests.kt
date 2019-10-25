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
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.repository.AdministratorRepository
import personal.ciai.vetclinic.service.AdministratorService

/**
 * Description:
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class AdministratorServiceTests {

    @Autowired
    lateinit var adminService: AdministratorService

    @MockBean
    lateinit var repository: AdministratorRepository

    @Test
    fun `basic test on get All`() {
        `when`(repository.findAll()).thenReturn(listAdmin)

        assertEquals(adminService.getAllAdministrator(), listAdmin.map { it.toDTO() })
    }

    @Test
    fun `basic test on get only One`() {
        `when`(repository.findById(1)).thenReturn(Optional.of(admin1))

        assertEquals(adminService.getAdministratorById(1), admin1.toDTO())
    }

    @Test
    fun `test on getAdministratorById() exception`() {
        `when`(repository.findById(4)).thenThrow(NotFoundException("not found"))

        assertThrows(NotFoundException::class.java) {
            adminService.getAdministratorById(4)
        }
    }

    @Test
    fun `test on add new Administrator`() {
        `when`(repository.save(Mockito.any(Administrator::class.java)))
            .then {
                val admin: Administrator = it.getArgument(0)
                assertEquals(admin.id, admin1.id)
                assertEquals(admin.name, admin1.name)
                assertEquals(admin.email, admin1.email)
                admin
            }

        adminService.save(admin1.toDTO())
    }

    @Test
    fun `Test function toDTO for the Administrator Class`() {
        val adminDTO = admin1.toDTO()
        `assert AdministratorDTO`(adminDTO)
    }

    private fun `assert AdministratorDTO`(admin: AdministratorDTO) {
        assertEquals(admin.id, (admin1.id))
        assertEquals(admin.name, (admin1.name))
        assertEquals(admin.email, (admin1.email))
        assertEquals(admin.address, (admin1.address))
        assertEquals(admin.phoneNumber, (admin1.phoneNumber))
        assertEquals(admin.username, (admin1.username))
        assertEquals(admin.password, "")
    }
}
