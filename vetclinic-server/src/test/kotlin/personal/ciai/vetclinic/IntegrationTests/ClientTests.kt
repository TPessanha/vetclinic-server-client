package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.controller.ClientController
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.security.SecurityService
import personal.ciai.vetclinic.service.ClientService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(ClientController::class)
class ClientTests {

    @Autowired
    lateinit var clientService: ClientService

    @MockBean
    lateinit var securityService: SecurityService

    companion object {

        val mapper = ObjectMapper().registerModule(KotlinModule())

        val clientsURL = "/clients"

        val clientExample = Client(0, "mail@gmail.com", "Tiago", 12345, "tiago99", "123", "Rua de Cima")
    }

    @Test
    fun `test client add`(){
        clientService.repository.save(clientExample)
        val savedClient = clientService.getClientEntityById(1)
        assertTrue(savedClient!=null)
    }
}
