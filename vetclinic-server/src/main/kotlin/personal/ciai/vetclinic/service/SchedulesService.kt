package personal.ciai.vetclinic.service

import java.time.YearMonth
import java.util.Date
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
import personal.ciai.vetclinic.util.asDate
import personal.ciai.vetclinic.util.asLocalDate
import personal.ciai.vetclinic.util.isValidateDate
import personal.ciai.vetclinic.util.numberOfWeeks
import personal.ciai.vetclinic.util.sameDate
import personal.ciai.vetclinic.util.toHours

@Service
class SchedulesService(
    @Autowired private val schedulesRepository: SchedulesRepository,
    @Autowired private val veterinarianRepository: VeterinarianRepository
) {
    private val SLOTS = 720
    private val MONTHLYHOURS = 160
    private val WEEKLYHOURS = 40
    private val NORMALWORKHOURS = 6
    private val RESTHOURS = 6
    private val MAXDAILYHOURS = 12

    fun getOneScheduleById(scheduleId: Int): SchedulesDTO = getOneScheduleEntity(scheduleId).toDTO()

    private fun getOneScheduleEntity(scheduleId: Int): Schedules = schedulesRepository.findById(scheduleId)
        .orElseThrow { NotFoundException("Schedules with Id $scheduleId not found") }

    fun getScheduleByVeterinarianIdAndStartTimeEntity(vetId: Int, startTime: Date): Schedules {
        return schedulesRepository.getVeterinarianAndStartDateIsEqual(
            vetId,
            startTime.time
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

    fun geVeterinarianSchedules(vetId: Int, afterDate: Date): List<SchedulesDTO> {
        if (afterDate.before(asDate(YearMonth.now().atDay(1)))) {
            throw PreconditionFailedException()
        }

        return schedulesRepository.findAllByVeterinarianAndStartDateIsGreaterThanEqual(
            vetId,
            afterDate.time
        ).map { it.toDTO() }
    }

    fun saveSchedule(schedules: List<SchedulesDTO>, vetId: Int) {

        val vet = getVeterinarianEntity(vetId)
        validateSchedules(schedules)
        vet.schedules = schedules.map { it.toEntity(vet) }.toMutableList()
        veterinarianRepository.save(vet)
        //  schedulesRepository.save(schedules.toEntity(getVeterinarianEntity(vetId)))
    }

    fun updateSchedule(schedules: SchedulesDTO) {
        if (schedules.status.equals(ScheduleStatus.Booked).not())
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

        if (a.startTime.equals(s.timeSlot.startDate).not() || a.endTime.compareTo(s.timeSlot.endDate.toLong()) > 0)
            throw PreconditionFailedException("Slot Time mismatch")
    }

    private fun getVeterinarianEntity(vetId: Int): Veterinarian {
        return veterinarianRepository.findById(vetId)
            .orElseThrow { NotFoundException("Veterinarian account with Id $id not found") }
    }

    private fun validateSchedules(schedules: List<SchedulesDTO>) {
        // List must have 720 slots
        if (schedules.size != SLOTS)
            throw PreconditionFailedException("Numbers of Slots mismatch")

        var durationMonth = 0
        var durationWeek = 0
        var durationDay = 0
        val sortedSchedules = schedules.sortedBy { it.startTime }.toMutableList()

        var monthDays = sortedSchedules.size
        val monthWeeks = (monthDays / 7).toInt()
        var count = 0

        // Validate the total duration for the mounth
        for (i in 0..monthWeeks) {
            // Validate per Week
            for (j in 0..min(7, monthDays)) {
                // The first shift of the day
                val day = asLocalDate(sortedSchedules.first().startTime)

                // Validate per Day
                for (k in 0..leftToCheck) {

                    if (!sameDate(day, (sortedSchedules.get(k).startTime))) {
                        if (durationDay > NORMALWORKHOURS) {
                            if (k < sortedSchedules.size - 1) {
                                if (toHours(
                                        sortedSchedules.get(k + 1).endTime - sortedSchedules.get(k).startTime
                                    ) < RESTHOURS
                                )
                                    throw PreconditionFailedException("Veterinarian need longes Rest hour")
                            }
                        }
                        break
                    }

                    val duration = toHours(sortedSchedules.get(i).endTime - sortedSchedules.get(i).startTime)
                    if (duration != 1)
                        throw PreconditionFailedException("Invalid slot duration")

                    durationDay += duration
                    sortedSchedules.removeAt(0)
                }

                durationWeek += durationDay
                durationDay = 0
                leftToCheck = sortedSchedules.size
                if ((durationDay) > MAXDAILYHOURS)
                    throw PreconditionFailedException("Invalid hours work hour per day")
            }

            monthDays -= 7

            if ((durationWeek) != WEEKLYHOURS)
                throw PreconditionFailedException("Invalid weekly work hour")

            durationMonth += durationWeek
            durationWeek = 0
        }

        if (durationMonth != MONTHLYHOURS)
            throw PreconditionFailedException("Invalid monthly work hour")
    }
}
