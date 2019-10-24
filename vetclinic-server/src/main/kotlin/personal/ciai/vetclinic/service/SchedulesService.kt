package personal.ciai.vetclinic.service

import java.time.YearMonth
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.dto.SchedulesDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.SchedulesRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.util.asDate
import personal.ciai.vetclinic.util.notWeekends

@Service
class SchedulesService(
    @Autowired private val schedulesRepository: SchedulesRepository,
    @Autowired private val veterinarianRepository: VeterinarianRepository
) {

    fun getOneScheduleById(scheduleId: Int): SchedulesDTO = getOneScheduleEntity(scheduleId).toDTO()

    private fun getOneScheduleEntity(scheduleId: Int): Schedules = schedulesRepository.findById(scheduleId)
        .orElseThrow { NotFoundException("Schedules with Id $scheduleId not found") }

    fun getScheduleByVeterinarianIdAndStartTimeEntity(vetId: Int, startTime: Date): Schedules {
        return schedulesRepository.getByVeterinarianAndStartDate(
            getVeterinarianEntity(vetId),
            startTime
        ).orElseThrow {
            NotFoundException("Schedules for Veterinarian with id $vetId and slot for date $startTime not found")
        }
    }

    fun getScheduleByIdAndStartTime(vetId: Int, startTime: Date): SchedulesDTO =
        getScheduleByVeterinarianIdAndStartTimeEntity(vetId, startTime).toDTO()

    fun geVeterinarianSchedules(vetId: Int): List<SchedulesDTO> {
        val afterDate = asDate(YearMonth.now().atDay(1))
        return geVeterinarianSchedules(vetId, afterDate)
    }

    @Cacheable("VetSchedules")
    fun geVeterinarianSchedules(vetId: Int, afterDate: Date): List<SchedulesDTO> {
        val vet = getVeterinarianEntity(vetId)

        if (afterDate.before(asDate(YearMonth.now().atDay(1)))) {
            throw PreconditionFailedException()
        }

        return schedulesRepository.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
            vet,
            afterDate
        ).map { it.toDTO() }
    }

    fun saveSchedule(schedules: SchedulesDTO, vetId: Int) {
        if (notWeekends(Date(schedules.startTime)))
            schedulesRepository.save(schedules.toEntity(getVeterinarianEntity(vetId)))
        else throw PreconditionFailedException()
    }

    fun updateSchedule(schedules: SchedulesDTO) {
        if (notWeekends(Date(schedules.startTime)))
            schedulesRepository.save(schedules.toEntity(getVeterinarianEntity(schedules.vetId)))
        else throw PreconditionFailedException()
    }

    fun isScheduleAvailable(vetId: Int, startTime: Long): Boolean {
        return getScheduleByIdAndStartTime(vetId, Date(startTime))
            .status.equals(ScheduleStatus.Available)
    }

    fun scheduleAppointment(appointmentDTO: AppointmentDTO) {
        val schedule = getScheduleByVeterinarianIdAndStartTimeEntity(
            appointmentDTO.veterinarian,
            Date(appointmentDTO.startTime)
        )

        validateAppointment(schedule, appointmentDTO)
        schedule.status = ScheduleStatus.Booked

        saveSchedule(schedule)
    }

    fun saveSchedule(schedules: Schedules) {
        schedulesRepository.save(schedules)
    }

    private fun validateAppointment(
        s: Schedules,
        a: AppointmentDTO
    ) {
        if (s.status == ScheduleStatus.Booked)
            throw PreconditionFailedException("Slot ${a.startTime} already taken")

        if (a.startTime.equals(s.startDate.time).not() || a.endTime.compareTo(s.endDate.time) > 0)
            throw PreconditionFailedException("Slot Time mismatch")
    }

    private fun getVeterinarianEntity(vetId: Int): Veterinarian {
        return veterinarianRepository.findById(vetId)
            .orElseThrow { NotFoundException("Veterinarian account with Id $id not found") }
    }
}
