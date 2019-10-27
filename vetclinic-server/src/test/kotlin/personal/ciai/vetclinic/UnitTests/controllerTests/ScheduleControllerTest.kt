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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.security.SecurityService
import personal.ciai.vetclinic.service.ScheduleService
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest {

    @Autowired
    lateinit var mvc: MockMvc
    @MockBean
    lateinit var securityService: SecurityService

    @MockBean
    lateinit var scheduleService: ScheduleService
    @Autowired
    lateinit var veterinarianRepository: VeterinarianRepository

    companion object {
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val schedulesUrl = "/veterinarians/1/schedules"
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `TEST - GET One Schedule (Not Found)`() {
        `when`(scheduleService.getOneScheduleById(1)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$schedulesUrl/1"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `Test GET all Schedule`() {

        `when`(scheduleService.getVeterinarianSchedule(1)).thenReturn(listOf(`schedule 1`.toDTO()))

        val result = mvc.perform(get("$schedulesUrl").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(1)))
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<ScheduleDTO>>(responseString)
        assertEquals(responseDTO.size, 1)
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["ADMIN"])
    fun `Test POST One Schedule`() {

        val json = mapper.writeValueAsString(`schedule 1`.toDTO())

        `when`(scheduleService.setVetSchedule(nonNullAny(ScheduleDTO::class.java)))
            .then {
                val schedule: ScheduleDTO = it.getArgument(0)
                assertEquals(`schedule 1`.veterinarian.id, schedule.vetId)
                assertEquals(`schedule 1`.year, schedule.year)
//                assertEquals(`schedule 1`.month, schedule.month)
            }

        mvc.perform(
            put("$schedulesUrl/2019/11")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
    }
}
