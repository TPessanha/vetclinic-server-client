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
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.IntegrationTests.VeterinarianTests
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.service.ScheduleService
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var scheduleService: ScheduleService
    @Autowired
    lateinit var veterinarianRepository: VeterinarianRepository

    companion object {
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val veterinarianURL = "/veterinarians/"
        val scheduleURL = "/schedules"
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `Test GET all Schedule`() {

        `when`(scheduleService.getVeterinarianSchedule(1)).thenReturn(listOf(`schedule 1`.toDTO()))

        val result = mvc.perform(get(veterinarianURL + 1 + scheduleURL))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(1)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<ScheduleDTO>>(responseString)
        assertEquals(responseDTO.size, 1)
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `Test GET One Veterinarian`() {
        `when`(scheduleService.getOneScheduleById(1)).thenReturn(`schedule 1`)

        val result = mvc.perform(get(veterinarianURL + 1 + scheduleURL))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<ScheduleDTO>(responseString)
        assertEquals(responseDTO.month, `schedule 1`.month)
        assertEquals(responseDTO.vetId, `schedule 1`.veterinarian.id)
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `TEST - GET One Veterinarian (Not Found)`() {
        `when`(scheduleService.getOneScheduleById(5)).thenThrow(NotFoundException("not found"))

        mvc.perform(get(veterinarianURL + "5" + scheduleURL))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `Test POST One Schedule`() {

        val json = mapper.writeValueAsString(`schedule 1`.toDTO())

        `when`(scheduleService.setVetSchedule(nonNullAny(ScheduleDTO::class.java)))
            .then { assertEquals(`schedule 1`.toDTO(), it.getArgument(0)) }

        mvc.perform(
            post(veterinarianURL + "1" + scheduleURL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)

        mvc.perform(
            MockMvcRequestBuilders
                .delete("${VeterinarianTests.veterinarianURL}/2")
        )
            .andExpect(status().isOk)
    }
}
