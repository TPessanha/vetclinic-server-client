package personal.ciai.vetclinic.service

import java.util.BitSet
import java.util.Optional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.ScheduleRepository

@Service
class ScheduleService(
    @Autowired
    val repository: ScheduleRepository,
    @Autowired
    val veterinarianService: VeterinarianService
) {
    fun getScheduleEntityByVetAndMonth(vet: Veterinarian, year: Int, month: Int): Optional<Schedule> {
        return repository.findByVeterinarianAndYearAndMonth(vet, year, month)
    }

    fun getScheduleEntityByVetIdAndMonth(vetId: Int, year: Int, month: Int): Optional<Schedule> {
        return repository.findByVetIdAndYearAndMonth(vetId, year, month)
    }

    fun saveSchedule(schedule: Schedule) {
        repository.save(schedule)
    }

    fun getVeterinarianSchedule(vetId: Int): List<ScheduleDTO> {
        return repository.findByVetId(vetId).map { it.toDTO() }
    }

    fun getVeterinarianScheduleByDate(vetId: Int, year: Int, month: Int): ScheduleDTO {
        return repository.findByVetIdAndYearAndMonth(vetId, year, month).get().toDTO()
    }

    fun setVetSchedule(schedule: ScheduleDTO) {
        val newEntity = schedule.toEntity(veterinarianService)
        val oldSchedule = getScheduleEntityByVetIdAndMonth(schedule.vetId, schedule.year, schedule.month)

        var toSave: ScheduleDTO

        if (oldSchedule.isPresent) {
            checkNewScheduleConflicts(oldSchedule.get(), newEntity)
            toSave = schedule.copy(id = oldSchedule.get().id)
        } else
            toSave = schedule.copy(id = 0)

        repository.save(toSave.toEntity(veterinarianService))
    }

    private fun checkNewScheduleConflicts(old: Schedule, new: Schedule) {
        val oldBookings = BitSet.valueOf(old.bookedBlocks)
        val newAvailability = BitSet.valueOf(new.availableBlocks)
        oldBookings.or(newAvailability)
        if (!oldBookings.equals(newAvailability))
            throw ConflictException("New schedule conflicts with already booked appointments")
    }

    fun getOneScheduleById(scheduleId: Int): Schedule =
        repository.findById(scheduleId).orElseThrow { NotFoundException("Schedule with id '$scheduleId' not found") }
}
