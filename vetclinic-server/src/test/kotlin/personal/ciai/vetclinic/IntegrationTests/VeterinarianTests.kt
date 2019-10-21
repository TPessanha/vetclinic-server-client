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
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vet2
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.service.VeterinarianService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VeterinarianTests {
    @Autowired
    lateinit var vetService: VeterinarianService

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val veterinarianURL = "/employees/1/veterinarians"
    }

    @Test
    fun `Add a new Veterinarian`() {
        assertTrue(vetService.getAllVeterinarian().size == 0)
        val dogJSON = mapper.writeValueAsString(vet2.toDTO())

        mvc.perform(
            MockMvcRequestBuilders
                .post(veterinarianURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dogJSON)
        )
            .andExpect(status().isOk)

        val resultList = mvc.perform(
            MockMvcRequestBuilders
                .get(veterinarianURL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(1)))
            .andReturn()

        val res = resultList.response.contentAsString
        val allVet = mapper.readValue<List<VeterinarianDTO>>(res)
        val result = mvc.perform(
            MockMvcRequestBuilders
                .get("$veterinarianURL/" + allVet[0].id)
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentVet = mapper.readValue<VeterinarianDTO>(responseString)

        assertNotEquals(vet2.id, persistentVet.id)
        assertEquals(vet2.name, persistentVet.name)
        assertEquals(vet2.username, persistentVet.username)
        assertEquals(vet2.email, persistentVet.email)
        assertEquals(vet2.address, persistentVet.address)

        mvc.perform(
            MockMvcRequestBuilders
                .delete("$veterinarianURL/" + allVet[0].id)
        )
            .andExpect(status().isOk)

        assertTrue(vetService.getAllVeterinarian().size == 1)
    }
}
