package personal.ciai.vetclinic.service

import java.time.YearMonth
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.repository.ScheduleRepository
import personal.ciai.vetclinic.util.DateTimeUtil

@Service
class ScheduleService(
    @Autowired private val scheduleRepository: ScheduleRepository,
    @Autowired private val veterinarianService: VeterinarianService
) {

    fun getOneSchedule(scheduleId: Int): ScheduleDTO = getOneScheduleEntity(scheduleId).toDTO()

    private fun getOneScheduleEntity(scheduleId: Int): Schedules = scheduleRepository.findById(scheduleId)
        .orElseThrow { NotFoundException("Schedules with Id $scheduleId not found") }

    fun geVeterinarianSchedules(vetId: Int): List<ScheduleDTO> {
        val afterDate = DateTimeUtil.asDate(YearMonth.now().atDay(1))
        return geVeterinarianSchedules(vetId, afterDate)
    }

    fun geVeterinarianSchedules(vetId: Int, afterDate: Date): List<ScheduleDTO> {
        if (veterinarianService.existsById(vetId)) {
            if (afterDate.before(DateTimeUtil.asDate(YearMonth.now().atDay(1)))) {
                throw ExpectationFailedException()
            }
        }
        return scheduleRepository.findAllByDateAfterWithVeterinarian(vetId, afterDate).map { it.toDTO() }
    }

    fun getSchedulesAfterDate(afterDate: Date): List<ScheduleDTO> {
        return scheduleRepository.findAllByDateAfter(afterDate).map { it.toDTO() }
    }

    fun saveSchedule(schedule: ScheduleDTO, vetId: Int) {
        if (DateTimeUtil.notWeekends(schedule.date))
            scheduleRepository.save(schedule.toEntity(veterinarianService))
        else throw ExpectationFailedException()
    }

    fun updateSchedule(schedule: ScheduleDTO, vetId: Int) {
        if (DateTimeUtil.notWeekends(schedule.date))
            scheduleRepository.save(schedule.toEntity(veterinarianService))
        else throw ExpectationFailedException()
    }
}
