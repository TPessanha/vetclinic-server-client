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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vet1
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vet2
import personal.ciai.vetclinic.ExampleObjects.exampleObjects.vetDTOList
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.VeterinarianService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class VeterinarianControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var vets: VeterinarianService

    companion object {
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val vetsURL = "/employees/1/veterinarians"
    }

    @Test
    fun `Test GET all Veterinarian`() {
        `when`(vets.getAllVeterinarian()).thenReturn(vetDTOList)

        val result = mvc.perform(get(vetsURL))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(vetDTOList.size)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<VeterinarianDTO>>(responseString)
        assertEquals(responseDTO, vetDTOList)
    }

    @Test
    fun `Test GET One Veterinarian`() {
        `when`(vets.getVeterinarianById(1)).thenReturn(vet1.toDTO())

        val result = mvc.perform(get("$vetsURL/1"))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<VeterinarianDTO>(responseString)
        assertEquals(responseDTO, vetDTOList[0])
    }

    @Test
    fun `Test GET One Veterinarian (Not Found)`() {
        `when`(vets.getVeterinarianById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$vetsURL/2"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Veterinarian`() {

        val vetsJSON = mapper.writeValueAsString(vet2.toDTO())

        `when`(vets.save(nonNullAny(VeterinarianDTO::class.java)))
            .then { assertEquals(vet2.toDTO(), it.getArgument(0)) }

        mvc.perform(
            post(vetsURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(vetsJSON)
        )
            .andExpect(status().isOk)
    }
}
