package personal.ciai.vetclinic.UnitTests.controllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personal.ciai.vetclinic.TestUtils
import personal.ciai.vetclinic.TestUtils.appointmentExample1DTO
import personal.ciai.vetclinic.TestUtils.appointmentListDTOs
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.service.AppointmentService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTests {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var appointmentService: AppointmentService

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val requestURL = "/clients/1/pets/1/appointments"
    }

    @Test
    fun `Test GET Pet Appointments`() {
        `when`(appointmentService.getPetAppointments(1)).thenReturn(appointmentListDTOs)

        val result = mvc.perform(get("$requestURL"))
            .andExpect(status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<AppointmentDTO>>(responseString)
        assertEquals(responseDTO, appointmentListDTOs)
    }

    @Test
    fun `Test GET One Appointment`() {
        `when`(appointmentService.getAppointmentById(1)).thenReturn(TestUtils.appointmentExample1DTO)

        val result = mvc.perform(MockMvcRequestBuilders.get("$requestURL/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<AppointmentDTO>(responseString)
        assertEquals(responseDTO, TestUtils.appointmentExample1DTO)
    }

    @Test
    fun `Test GET One Appointment (Not Found)`() {
        `when`(appointmentService.getAppointmentById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$requestURL/2"))
            .andExpect(status().is4xxClientError)
    }

    fun <T> nonNullAny(t: Class<T>): T = any(t)

    @Test
    fun `Test POST One Pet`() {
        val appointmentJSON = mapper.writeValueAsString(appointmentExample1DTO)

        `when`(appointmentService.saveAppointment(nonNullAny(AppointmentDTO::class.java), anyInt()))
            .then { assertEquals(appointmentExample1DTO.copy(pet = 1), it.getArgument(0)) }

        mvc.perform(post(requestURL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(appointmentJSON))
            .andExpect(status().isOk)
    }
}
