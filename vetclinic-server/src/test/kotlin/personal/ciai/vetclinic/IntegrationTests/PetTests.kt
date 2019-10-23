package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.service.ClientService
import personal.ciai.vetclinic.service.PetService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "password", roles = ["CLIENT"])
class PetTests {
    @Autowired
    lateinit var petService: PetService

    @Autowired
    lateinit var clientService: ClientService

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val petsURL = "/clients/1/pets"

        val client1 = Client(0, "mail", "name", 52235, "Username", "123", "asfasf")
        val petExample = Pet(0, "cat", 2, client1)
    }

//    @Test
//    fun `test client add`(){
//        clientService.repository.save(client1)
//        val savedClient = clientService.getClientEntityById(1)
//        assertTrue(savedClient!=null)
//    }

    @Test
    @Transactional
    fun `Client add a new pet`() {
        val nPets = petService.getAllPets().size
        assertTrue(petService.getAllPets().size == 2)

        val dogJSON = mapper.writeValueAsString(petExample.toDTO().copy(id = 0, owner = 1))

        mvc.perform(
            post(petsURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dogJSON)
        )
            .andExpect(status().isOk)

        mvc.perform(
            get(petsURL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(nPets + 1)))

        val result = mvc.perform(
            MockMvcRequestBuilders
                .get("$petsURL/3")
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentPet = mapper.readValue<PetDTO>(responseString)

        assertEquals(petExample.species, persistentPet.species)
        assertEquals(petExample.medicalRecord, persistentPet.medicalRecord)
        assertEquals(petExample.physicalDescription, persistentPet.physicalDescription)
        assertEquals(petExample.notes, persistentPet.notes)
        assertEquals(petExample.age, persistentPet.age)

        // clenup
        mvc.perform(
            delete("$petsURL/3")
        )
            .andExpect(status().isOk)

        assertEquals(petService.getAllPets().size, nPets)
    }

    @Test
    @Transactional
    fun `test updatePet`() {
        val result = mvc.perform(
            get("$petsURL/1")
        )
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val persistentPet = mapper.readValue<PetDTO>(responseString)

        assertNotEquals(200, persistentPet.age)

        val newPet = persistentPet.copy(age = 200)
        val newPetJSON = mapper.writeValueAsString(newPet)

        mvc.perform(
            put("$petsURL/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPetJSON)
        )
            .andExpect(status().isOk)

        val newResult = mvc.perform(
            get("$petsURL/1")
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
