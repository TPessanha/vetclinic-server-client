package personal.ciai.vetclinic.service

import java.time.YearMonth
import kotlin.math.min
import org.springframework.beans.factory.annotation.Autowired
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
import personal.ciai.vetclinic.util.asLocalDate
import personal.ciai.vetclinic.util.asLocalDateTime
import personal.ciai.vetclinic.util.isBefore
import personal.ciai.vetclinic.util.isValidateDate
import personal.ciai.vetclinic.util.numberOfWeeks
import personal.ciai.vetclinic.util.toHours

@Service
class SchedulesService(
    @Autowired private val schedulesRepository: SchedulesRepository,
    @Autowired private val veterinarianRepository: VeterinarianRepository
) {
    private val MONTHLYHOURS = 160
    private val WEEKLYHOURS = 40
    private val NORMALWORKHOURS = 6
    private val RESTHOURS = 6
    private val MAXDAILYHOURS = 12

    fun getOneScheduleById(scheduleId: Int): SchedulesDTO = getOneScheduleEntity(scheduleId).toDTO()

    private fun getOneScheduleEntity(scheduleId: Int): Schedules = schedulesRepository.findById(scheduleId)
        .orElseThrow { NotFoundException("Schedules with Id $scheduleId not found") }

    fun getVeterinarianSchedules(vetId: Int): List<SchedulesDTO> {
        return (schedulesRepository.findAllByVeterinarian(vetId).orElseThrow {
            throw NotFoundException("Schedules for Veterinarian with id $vetId  not found")
        }).map { it.toDTO() }
    }

    fun getScheduleByVeterinarianAndStartTime(vetId: Int, startTime: Long): SchedulesDTO =
        getScheduleByVeterinarianIdAndStartTimeEntity(vetId, startTime).toDTO()

    fun getScheduleByVeterinarianIdAndStartTimeEntity(vetId: Int, startTime: Long): Schedules {
        getVeterinarianEntity(vetId)
        return schedulesRepository.getVeterinarianAndStartDateIsEqual(
            vetId,
            startTime
        ).orElseThrow {
            NotFoundException("Schedules for Veterinarian with id $vetId and slot for date $startTime not found")
        }
    }

    fun getVeterinarianSchedules(vetId: Int, afterDate: Long): List<SchedulesDTO> {
        getVeterinarianEntity(vetId)
        return schedulesRepository.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
            vetId,
            afterDate
        ).map { it.toDTO() }
    }

    fun addMonthlySchedule(schedules: List<SchedulesDTO>, vetId: Int) {

        val vet = getVeterinarianEntity(vetId)
        validateSchedules(schedules)
        val toSave = schedules.map { it.toEntity(vet) }
        schedulesRepository.saveAll(toSave)
    }

    fun update(schedules: SchedulesDTO) {
        val saved = getOneScheduleEntity(schedules.id)
        if (saved.status.equals(ScheduleStatus.Booked).not() &&
            !isBefore(saved.timeSlot.startDate.toLong())
        ) {
            schedulesRepository.save(schedules.toEntity(getVeterinarianEntity(schedules.vetId)))
        } else throw PreconditionFailedException("Schedule can no be changed")
    }

    fun updateEntity(schedules: Schedules) {
        val saved = getOneScheduleEntity(schedules.id)
        if (saved.status.equals(ScheduleStatus.Booked).not() &&
            isBefore(saved.timeSlot.startDate.toLong())
        ) {
            schedulesRepository.save(schedules)
        } else throw PreconditionFailedException("Schedule can no be changed")
    }

    fun scheduleAppointment(appointmentDTO: AppointmentDTO) {
        val schedule = getScheduleByVeterinarianIdAndStartTimeEntity(
            appointmentDTO.veterinarian,
            appointmentDTO.startTime
        )

        validateAppointment(schedule, appointmentDTO)
        schedule.status = ScheduleStatus.Booked

        schedulesRepository.save(schedule)
    }

    fun validateAppointment(
        s: Schedules,
        a: AppointmentDTO
    ) {
        if (s.status == ScheduleStatus.Booked)
            throw PreconditionFailedException("Slot ${a.startTime} already taken")

        if (a.startTime != s.timeSlot.startDate.toLong() || a.endTime != s.timeSlot.endDate.toLong())
            throw PreconditionFailedException("Slot Time mismatch")
    }

    private fun getVeterinarianEntity(vetId: Int): Veterinarian {
        return veterinarianRepository.findById(vetId)
            .orElseThrow { NotFoundException("Veterinarian account with Id $id not found") }
    }

    private fun validateSchedules(schedules: List<SchedulesDTO>) {
        // List must have 160 slots with 1 hours each
        if (schedules.size != MONTHLYHOURS)
            throw PreconditionFailedException("Numbers of Slots mismatch")

        var durationMonth = 0
        var durationWeek = 0
        var durationDay = 0
        val sortedSchedules = schedules.sortedBy { it.startTime }.toMutableList()

        val startDate = asLocalDateTime(sortedSchedules.first().startTime)
        println(startDate)
        if (!isValidateDate(startDate))
            throw PreconditionFailedException("Can not set Schedule for a the month")

        var monthDays = YearMonth.from(startDate).lengthOfMonth()
        val monthWeeks = numberOfWeeks(startDate) + 1
        var leftToCheck = sortedSchedules.size

        // Validate the total duration for the mounth
        for (i in 0..monthWeeks) {
            // Validate per Week
            for (j in 0..min(7, monthDays)) {
                // The first shift of the day
                val day = asLocalDate(sortedSchedules.first().startTime)

                // Validate per Day
                for (k in 0..leftToCheck) {

                    if (day.isBefore(asLocalDate(sortedSchedules.first().startTime))) {
                        if (durationDay > NORMALWORKHOURS) {
                            if (sortedSchedules.size > 1) {
                                if (toHours(
                                        sortedSchedules.get(0).endTime - sortedSchedules.get(1).startTime
                                    ) < RESTHOURS
                                )
                                    throw PreconditionFailedException("Veterinarian need longer Rest hour")
                            }
                        }
                        break
                    }

                    val duration = toHours(sortedSchedules.get(i).endTime - sortedSchedules.get(i).startTime)
                    if (duration != 1)
                        throw PreconditionFailedException("Invalid slot duration")

                    durationDay += duration
                    sortedSchedules.removeAt(0)
                    if (sortedSchedules.isEmpty())
                        break
                }

                durationWeek += durationDay
                durationDay = 0
                leftToCheck = sortedSchedules.size

                if ((durationDay) > MAXDAILYHOURS)
                    throw PreconditionFailedException("Invalid hours work hour per day")
                if (sortedSchedules.isEmpty())
                    break
            }

            monthDays -= 7
            durationMonth += durationWeek
            durationWeek = 0

            if ((durationWeek) > WEEKLYHOURS)
                throw PreconditionFailedException("Invalid weekly work hour")

            if (sortedSchedules.isEmpty())
                break
        }

        if (durationMonth != MONTHLYHOURS)
            throw PreconditionFailedException("Invalid monthly work hour")
    }
}
