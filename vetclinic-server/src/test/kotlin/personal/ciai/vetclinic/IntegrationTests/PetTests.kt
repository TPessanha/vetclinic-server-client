package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.security.Principal
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
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
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.security.SecurityService
import personal.ciai.vetclinic.service.ClientService
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class PetTests {
    @Autowired
    lateinit var petService: PetService

    @Autowired
    lateinit var clientService: ClientService

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var securityService: SecurityService

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val petsURL = "/clients/2/pets"

    }

    @Test
    @Transactional
    fun `Client add a new pet`() {
        `when`(securityService.isPrincipalAccountOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)
        `when`(securityService.isPetOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)
        val token = TestUtils.generateTestToken("user2", listOf("ROLE_CLIENT"))


        val DTO = PetDTO(0, "cat", 2, 1)

        val dogJSON = mapper.writeValueAsString(DTO.copy(id = 0, owner = 1))

        mvc.perform(
            post(petsURL).header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dogJSON)
        )
            .andExpect(status().isOk)

        mvc.perform(
            get(petsURL).header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(3)))

        val result = mvc.perform(
                get("$petsURL/4").header("Authorization", token)
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentPet = mapper.readValue<PetDTO>(responseString)

        assertEquals(DTO.species, persistentPet.species)
        assertEquals(DTO.medicalRecord, persistentPet.medicalRecord)
        assertEquals(DTO.physicalDescription, persistentPet.physicalDescription)
        assertEquals(DTO.notes, persistentPet.notes)
        assertEquals(DTO.age, persistentPet.age)

        // clenup
        mvc.perform(
            delete("$petsURL/4").header("Authorization", token)
        )
            .andExpect(status().isOk)

        mvc.perform(
            get(petsURL).header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(2)))

    }

    fun <T> nonNullAny(t: Class<T>): T = Mockito.any(t)

    @Test
    @Transactional
    fun `test updatePet`() {
        `when`(securityService.isPetOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)
        `when`(securityService.isPrincipalAccountOwner(nonNullAny(Principal::class.java), anyInt())).thenReturn(true)

        val token = TestUtils.generateTestToken("user2", listOf("ROLE_CLIENT"))

        val result = mvc.perform(
            get("$petsURL/1").header("Authorization", token)
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentPet = mapper.readValue<PetDTO>(responseString)

        assertNotEquals(200, persistentPet.age)

        val newPet = persistentPet.copy(age = 200)
        val newPetJSON = mapper.writeValueAsString(newPet)

        mvc.perform(
            put("$petsURL/1").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPetJSON)
        )
            .andExpect(status().isOk)

        val newResult = mvc.perform(
            get("$petsURL/1").header("Authorization", token)
        )
            .andExpect(status().isOk)
            .andReturn()

        val newResponseString = newResult.response.contentAsString
        val newPersistentPet = mapper.readValue<PetDTO>(newResponseString)

        assertEquals(200, newPersistentPet.age)
        assertAll("All other values the same",
            { assertEquals(persistentPet.id, newPersistentPet.id) },
            { assertEquals(persistentPet.medicalRecord, newPersistentPet.medicalRecord) },
            { assertEquals(persistentPet.notes, newPersistentPet.notes) },
            { assertEquals(persistentPet.species, newPersistentPet.species) },
            { assertEquals(persistentPet.owner, newPersistentPet.owner) },
            { assertEquals(persistentPet.physicalDescription, newPersistentPet.physicalDescription) },
            { assertEquals(persistentPet.photo, newPersistentPet.photo) }
        )
    }
}
