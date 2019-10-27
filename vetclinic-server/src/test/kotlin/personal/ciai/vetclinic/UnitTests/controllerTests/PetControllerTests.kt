package personal.ciai.vetclinic.UnitTests.controllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.security.Principal
import javax.transaction.Transactional
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.anyInt
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
import org.springframework.web.context.WebApplicationContext
import personal.ciai.vetclinic.TestUtils
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.TestUtils.petList
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.security.SecurityService
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTests {
    @MockBean
    lateinit var pets: PetService

    @MockBean
    lateinit var securityService: SecurityService

    @Autowired
    lateinit var context: WebApplicationContext

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val petsURL = "/clients/2/pets"

        val token = TestUtils.generateTestToken("user2", listOf("ROLE_CLIENT"))
    }

    @Test
    @Transactional
//    @WithMockUser(username = "user2", password = "password", roles = ["CLIENT"])
    fun `Test GET client pets`() {
        val dtoList = petList.map { it.toDTO() }
        `when`(pets.getClientPets(anyInt())).thenReturn(dtoList)
        `when`(securityService.isPrincipalAccountOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)

        val result = mvc.perform(get(petsURL).header("Authorization", token))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(petList.size)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<PetDTO>>(responseString)
        assertEquals(responseDTO, dtoList)
    }

    @Test
    @Transactional
    fun `Test GET One Pet`() {
        val dtoList = petList.map { it.toDTO() }

        `when`(pets.getPetById(anyInt())).thenReturn(dogExample.toDTO())
        `when`(securityService.isPetOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)

        val token = TestUtils.generateTestToken("user2", listOf("ROLE_CLIENT"))

        val result = mvc.perform(get("$petsURL/1").header("Authorization", token))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<PetDTO>(responseString)
        assertEquals(responseDTO, dtoList[0])
    }

    @Test
    fun `Test GET One Pet (Not Found)`() {
        `when`(pets.getPetById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$petsURL/2").header("Authorization", token))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Pet`() {
        val dtoL = petList.get(0).toDTO()

        val petJSON = mapper.writeValueAsString(dtoL)

        `when`(pets.addPet(nonNullAny(PetDTO::class.java)))
            .then { assertEquals(dtoL.copy(owner = 2), it.getArgument(0)) }
        `when`(securityService.isPrincipalAccountOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)

        mvc.perform(
            post(petsURL).header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJSON)
        )
            .andExpect(status().isOk)
    }
}
