package personal.ciai.vetclinic.UnitTests.controllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.anyInt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.MockMvcConfigurer
import personal.ciai.vetclinic.TestUtils.dogExample
import personal.ciai.vetclinic.TestUtils.petList
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.PetService
import org.springframework.web.context.WebApplicationContext
import personal.ciai.vetclinic.TestUtils
import javax.transaction.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
//@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@WithUserDetails("rui", userDetailsServiceBeanName = "UserDetailsService")
class PetControllerTests {
    @MockBean
    lateinit var pets: PetService

    // To avoid all annotations JsonProperties in data classes
    // see: https://github.com/FasterXML/jackson-module-kotlin
    // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
    val mapper = ObjectMapper().registerModule(KotlinModule())

    val petsURL = "/clients/2/pets"

    val token = TestUtils.generateTestToken("user2", listOf("ROLE_CLIENT"))

    @Autowired
    lateinit var context: WebApplicationContext
    lateinit var mvc: MockMvc

    @BeforeAll
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build();
    }

//    @BeforeEach
//    fun login() {
//        this.mvc.perform(
//            post("/login")
//                .param("username", "rui")
//                .param("password", "password")
//        )
//            .andExpect(status().isOk)
//    }

    @Test
    @Transactional
//    @WithMockUser(username = "user2", password = "password", roles = ["CLIENT"])
    fun `Test GET client pets`() {
        val dtoList = petList.map { it.toDTO() }
        `when`(pets.getClientPets(anyInt())).thenReturn(dtoList)

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

        mvc.perform(get("$petsURL/2"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Pet`() {
        val dtoList = petList.map { it.toDTO() }

        val petJSON = mapper.writeValueAsString(dtoList[0])

        `when`(pets.addPet(nonNullAny(PetDTO::class.java)))
            .then { assertEquals(dtoList[0].copy(owner = 1), it.getArgument(0)) }

        mvc.perform(
            post(petsURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJSON)
        )
            .andExpect(status().isOk)
    }
}
