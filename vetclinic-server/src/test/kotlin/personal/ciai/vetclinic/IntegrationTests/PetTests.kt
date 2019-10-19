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
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PetTests {
    @Autowired
    lateinit var petService: PetService

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val petsURL = "/clients/1/pets"
    }

    @Test
    fun `Client add a new pet`() {
        assertTrue(petService.getAllPets().isEmpty())
        val dogJSON = mapper.writeValueAsString(dogExample.toDTO().copy(id = 0))

        mvc.perform(
            MockMvcRequestBuilders
                .post(petsURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dogJSON)
        )
            .andExpect(status().isOk)

        mvc.perform(
            MockMvcRequestBuilders
                .get(petsURL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(1)))

        val result = mvc.perform(
            MockMvcRequestBuilders
                .get("$petsURL/1")
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentDog = mapper.readValue<PetDTO>(responseString)

        assertNotEquals(dogExample.id, persistentDog.id)
        assertEquals(dogExample.species, persistentDog.species)
        assertEquals(dogExample.medicalRecord, persistentDog.medicalRecord)
        assertEquals(dogExample.physicalDescription, persistentDog.physicalDescription)
        assertEquals(dogExample.notes, persistentDog.notes)
        assertEquals(dogExample.age, persistentDog.age)

        // clenup
        mvc.perform(
            MockMvcRequestBuilders
                .delete("$petsURL/1")
        )
            .andExpect(status().isOk)

        assertTrue(petService.getAllPets().isEmpty())
    }
}
