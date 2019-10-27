package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.TestUtils
import personal.ciai.vetclinic.dto.ClientDTO
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.security.SecurityService
import personal.ciai.vetclinic.service.ClientService
import java.security.Principal

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientTests {

    @Autowired
    lateinit var clientService: ClientService

//    @MockBean
//    lateinit var securityService: SecurityService

    @Autowired
    lateinit var mvc: MockMvc

    companion object {

        val mapper = ObjectMapper().registerModule(KotlinModule())

        val clientsURL = "/clients"

        val clientExample = Client(0, "mail@gmail.com", "Tiago", 12345, "tiago99", "123", "Rua de Cima")
    }

    fun <T> nonNullAny(t: Class<T>): T = Mockito.any(t)

    @Test
    @Transactional
    fun `test get client details`() {
        SecurityContextHolder.clearContext()

        val token = TestUtils.generateTestToken("user2", listOf("ROLE_CLIENT"))

        val result = mvc.perform(
            get("${clientsURL}/2").header("Authorization", token)
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentClient = mapper.readValue<ClientDTO>(responseString)

        assertEquals("user2",persistentClient.username)
    }

    @Test
    @Transactional
    fun `test get client details (Not authorized)`() {
        SecurityContextHolder.clearContext()

        val token = TestUtils.generateTestToken("user3", listOf("ROLE_CLIENT"))

        mvc.perform(
            get("${clientsURL}/2").header("Authorization", token)
        )
            .andExpect(status().isForbidden)
    }
}
