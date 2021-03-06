package personal.ciai.vetclinic.UnitTests.controllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.admin2
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.listAdminDTO
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.dto.BasicSafeInfoDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.AdministratorService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
class AdministratorControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var admin: AdministratorService

    companion object {
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val adminURL = "/administrators"
    }

    @Test
    fun `Test GET all Administrator`() {
        `when`(admin.getAllAdministrator()).thenReturn(listAdminDTO)

        val result = mvc.perform(get(adminURL))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(listAdminDTO.size)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<BasicSafeInfoDTO>>(responseString)
        assertEquals(responseDTO.size, listAdminDTO.size)
    }

    @Test
    fun `Test GET One Administrator`() {
        `when`(admin.getAdministratorById(1)).thenReturn(admin2.toDTO())

        val result = mvc.perform(get("$adminURL/1"))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<AdministratorDTO>(responseString)
        assertEquals(responseDTO, admin2.toDTO())
    }

    @Test
    fun `Test GET One Administrator (Not Found)`() {
        `when`(admin.getAdministratorById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$adminURL/2"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Administrator`() {

        val adminJSON = mapper.writeValueAsString(admin2.toDTO())

        `when`(admin.save(nonNullAny(AdministratorDTO::class.java)))
            .then { assertEquals(admin2.toDTO(), it.getArgument(0)) }

        mvc.perform(
            post(adminURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(adminJSON)
        )
            .andExpect(status().isOk)
    }
}
