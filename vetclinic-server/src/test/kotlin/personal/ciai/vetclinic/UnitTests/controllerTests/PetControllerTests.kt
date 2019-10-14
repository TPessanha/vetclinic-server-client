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
import org.mockito.Mockito.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.dogExample
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.petList
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var pets: PetService

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val petsURL = "/pets"
    }

    @Test
    fun `Test GET all pets`() {
        `when`(pets.getAllPets()).thenReturn(petList)

        val result = mvc.perform(get(petsURL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize<Any>(petList.size)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<PetDTO>>(responseString)
        assertEquals(responseDTO, petList)
    }

    @Test
    fun `Test GET One Pet`() {
        `when`(pets.getPetById(1)).thenReturn(dogExample.toDTO())

        val result = mvc.perform(get("$petsURL/1"))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<PetDTO>(responseString)
        assertEquals(responseDTO, petList[0])
    }

    @Test
    fun `Test GET One Pet (Not Found)`() {
        `when`(pets.getPetById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$petsURL/2"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Pet`() {
        val louro = PetDTO(0, "Papagaio", 4, "client0124", notes = "hi hi")

        val louroJSON = mapper.writeValueAsString(louro)

        `when`(pets.savePet(nonNullAny(PetDTO::class.java), eq(-1)))
            .then { assertEquals(louro, it.getArgument(0)) }

        mvc.perform(post(petsURL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(louroJSON))
            .andExpect(status().isOk)
    }
}
