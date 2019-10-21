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
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.admin2
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.listAdminDTO
import personal.ciai.vetclinic.IntegrationTests.AdministrativeTests
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.AdministrativeService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class AdministrativeControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var admin: AdministrativeService

    companion object {
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val adminURL = "/employees/1/administratives"
    }

    @Test
    fun `Test GET all Administrative`() {
        `when`(admin.getAllAdministrative()).thenReturn(listAdminDTO)

        val result = mvc.perform(get(adminURL))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(listAdminDTO.size)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<AdministrativeDTO>>(responseString)
        assertEquals(responseDTO, listAdminDTO)
    }

    @Test
    fun `Test GET One Administrative`() {
        `when`(admin.getAdministrativeById(1)).thenReturn(admin2.toDTO())

        val result = mvc.perform(get("$adminURL/1"))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<AdministrativeDTO>(responseString)
        assertEquals(responseDTO, admin2.toDTO())
    }

    @Test
    fun `Test GET One Administrative (Not Found)`() {
        `when`(admin.getAdministrativeById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$adminURL/2"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Administrative`() {

        val adminJSON = mapper.writeValueAsString(admin2.toDTO())

        `when`(admin.save(nonNullAny(AdministrativeDTO::class.java)))
            .then { assertEquals(admin2.toDTO(), it.getArgument(0)) }

        mvc.perform(
            post(adminURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(adminJSON)
        )
            .andExpect(status().isOk)

        mvc.perform(
            MockMvcRequestBuilders
                .delete("${AdministrativeTests.adminsURL}/1")
        )
            .andExpect(status().isOk)
    }
}
