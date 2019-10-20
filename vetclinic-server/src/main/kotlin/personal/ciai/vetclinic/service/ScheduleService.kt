package personal.ciai.vetclinic.service

import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.repository.ScheduleRepository

@Service
class ScheduleService(@Autowired private val scheduleRepository: ScheduleRepository) {

    fun getOneSchedule(scheduleId: Int): ScheduleDTO = scheduleRepository.getOne(scheduleId).toDTO()

    fun geVeterinarianSchedules(vetId: Int): List<ScheduleDTO> {

        return emptyList()
    }

    fun geVeterinarianSchedules(vetId: Int, fromDate: Date): List<ScheduleDTO> {

        return emptyList()
    }

    fun saveSchedule(schedule: ScheduleDTO, vetId: Int) {
    }
}
