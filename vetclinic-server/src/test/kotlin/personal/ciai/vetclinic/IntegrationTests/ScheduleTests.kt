package personal.ciai.vetclinic.IntegrationTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.service.ScheduleService
import personal.ciai.vetclinic.utils.ScheduleUtils.`schedule 1`
import personal.ciai.vetclinic.utils.VeterinarianUtils.`veterinarian 1`

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "vet", password = "123", roles = ["VET"])
class ScheduleTests {
    @Autowired
    lateinit var scheduleService: ScheduleService

    @Autowired
    lateinit var veterinarianRepository: VeterinarianRepository

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val vetURL = "/veterinarian/"
        val scheduleURL = "/schedules"
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", password = "password", roles = ["VET", "ADMIN"])
    fun `Add a new Schedule (Failed - PreCondition)`() {
        val vet = veterinarianRepository.save(`veterinarian 1`)
        val nSchedules = scheduleService.getVeterinarianSchedule(vet.id)
        val scheduleJSON = ScheduleTests.mapper.writeValueAsString(`schedule 1`.toDTO())
        mvc.perform(
            MockMvcRequestBuilders
                .put(vetURL + vet.id + scheduleURL + "/" + `schedule 1`.year + "/" + `schedule 1`.month)
                .contentType(MediaType.APPLICATION_JSON)
                .content(scheduleJSON)
        )
            .andExpect(status().is4xxClientError)
    }
}
