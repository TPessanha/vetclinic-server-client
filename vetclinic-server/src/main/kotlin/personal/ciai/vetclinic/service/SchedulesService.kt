package personal.ciai.vetclinic.service

import java.time.YearMonth
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.SchedulesDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.repository.SchedulesRepository
import personal.ciai.vetclinic.util.asDate
import personal.ciai.vetclinic.util.notWeekends

@Service
class SchedulesService(
    @Autowired private val schedulesRepository: SchedulesRepository,
    @Autowired private val veterinarianService: VeterinarianService
) {

    fun getOneScheduleById(scheduleId: Int): SchedulesDTO = getOneScheduleEntity(scheduleId).toDTO()

    private fun getOneScheduleEntity(scheduleId: Int): Schedules = schedulesRepository.findById(scheduleId)
        .orElseThrow { NotFoundException("Schedules with Id $scheduleId not found") }

    fun getScheduleByIdAndStartTime(vetId: Int, startTime: Date): Schedules {
        return schedulesRepository.getByVeterinarianAndStartDate(
            veterinarianService.getVeterinarianEntity(vetId),
            startTime
        ).get()
    }

    fun geVeterinarianSchedules(vetId: Int): List<SchedulesDTO> {
        val afterDate = asDate(YearMonth.now().atDay(1))
        return geVeterinarianSchedules(vetId, afterDate)
    }

    @Cacheable("VetSchedules")
    fun geVeterinarianSchedules(vetId: Int, afterDate: Date): List<SchedulesDTO> {
        if (veterinarianService.existsById(vetId)) {
            if (afterDate.before(asDate(YearMonth.now().atDay(1)))) {
                throw PreconditionFailedException()
            }
        }
        return schedulesRepository.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
            veterinarianService.getVeterinarianEntity(vetId),
            afterDate
        ).map { it.toDTO() }
    }

    fun saveSchedule(schedules: SchedulesDTO, vetId: Int) {
        if (notWeekends(Date(schedules.startTime)))
            schedulesRepository.save(schedules.toEntity(veterinarianService))
        else throw PreconditionFailedException()
    }

    fun updateSchedule(schedules: SchedulesDTO, vetId: Int) {
        if (notWeekends(Date(schedules.startTime)))
            schedulesRepository.save(schedules.toEntity(veterinarianService))
        else throw PreconditionFailedException()
    }

    fun isScheduleAvailable(vetId: Int, startTime: Long): Boolean {
        return getScheduleByIdAndStartTime(vetId, Date(startTime))
            .status.equals(ScheduleStatus.Available)
    }
}
