package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import personal.ciai.vetclinic.controller.ClientController
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
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val clientsURL = "/users/1/clients"
    }
}
