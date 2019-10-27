package personal.ciai.vetclinic.UnitTests.controllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import personal.ciai.vetclinic.security.SecurityService
import personal.ciai.vetclinic.service.ClientService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTests {
    @MockBean
    lateinit var clients: ClientService

    @MockBean
    lateinit var securityService: SecurityService

    @Autowired
    lateinit var context: WebApplicationContext

    @Autowired
    lateinit var mvc: MockMvc

    companion object {

        val mapper = ObjectMapper().registerModule(KotlinModule())

        val clientsURL = "/clients"
    }

    fun `Test GET client appointments`() {
    }

    fun `Test GET client info`() {
    }

    fun `Test GET client photo`() {
    }
}
