package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.admin1
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.service.AdministratorService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
class AdministratorTests {
    @Autowired
    lateinit var adminService: AdministratorService

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val adminsURL = "/employees/1/administratives"
    }

    @Test
    fun `Add a new Administrator`() {
        val nAdmins = adminService.getAllAdministrator().size
        val dogJSON = mapper.writeValueAsString(admin1.toDTO())

        mvc.perform(
            MockMvcRequestBuilders
                .post(adminsURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dogJSON)
        )
            .andExpect(status().isOk)

        val resultList = mvc.perform(
            MockMvcRequestBuilders
                .get(adminsURL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(nAdmins + 1)))
            .andReturn()

        val res = resultList.response.contentAsString
        val allAdmin = mapper.readValue<List<AdministratorDTO>>(res)

        val result = mvc.perform(
            MockMvcRequestBuilders
                .get("$adminsURL/" + allAdmin[1].id)
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentAdmin = mapper.readValue<AdministratorDTO>(responseString)

        assertNotEquals(admin1.id, persistentAdmin.id)
        assertEquals(admin1.name, persistentAdmin.name)
        assertEquals(admin1.username, persistentAdmin.username)
        assertEquals(admin1.email, persistentAdmin.email)
        assertEquals(admin1.address, persistentAdmin.address)

        mvc.perform(
            MockMvcRequestBuilders
                .delete("$adminsURL/" + allAdmin[1].id)
        )
            .andExpect(status().isOk)

        assertTrue(adminService.getAllAdministrator().size == nAdmins)
    }
}
