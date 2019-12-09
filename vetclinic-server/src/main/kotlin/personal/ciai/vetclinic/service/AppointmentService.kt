package personal.ciai.vetclinic.service

import java.util.Calendar
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.AppointmentStatus
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.util.now

@Service
class AppointmentService(
    @Autowired
    val repository: AppointmentRepository,
    @Autowired
    val petService: PetService,
    @Autowired
    val clientService: ClientService,
    @Autowired
    val veterinarianService: VeterinarianService,
    @Autowired
    val scheduleService: ScheduleService
) {
    fun getAllAppointments() = repository.findAll().map { it.toDTO() }

    fun getAppointmentById(id: Int) = getAppointmentEntityById(id).toDTO()

    fun getAppointmentEntityById(id: Int): Appointment =
        repository.findById(id).orElseThrow { NotFoundException("Appointment with id ($id) not found") }

    @CacheEvict("PetAppointments", key = "#appointmentDTO.pet.id")
    private fun saveAppointment(appointmentDTO: AppointmentDTO, id: Int = 0) {
        val pet = petService.getPetEntityById(appointmentDTO.pet)
        if (!pet.enabled)
            throw PreconditionFailedException("Cannot book appointments for disabled pets")

        val newAppointment = appointmentDTO.toEntity(id, petService, clientService, veterinarianService)
        val vet = veterinarianService.getVeterinarianEntity(appointmentDTO.veterinarian)

        val schedule = scheduleService.getScheduleEntityByVetAndMonth(
            vet,
            appointmentDTO.year,
            appointmentDTO.month
        )

        if (schedule.isPresent) {
            schedule.get().bookAppointment(TimeSlot(appointmentDTO.startTime, appointmentDTO.endTime))
            scheduleService.saveSchedule(schedule.get())
            repository.save(newAppointment)
        } else
            throw PreconditionFailedException("The veterinarian is does not have a schedule for that time")
    }

    fun updateAppointment(appointmentDTO: AppointmentDTO, id: Int) {
        if (id <= 0 || !repository.existsById(id))
            throw NotFoundException("Appointment with id ($id) not found")

        if (AppointmentStatus.values()[appointmentDTO.status] != AppointmentStatus.Pending)
            throw PreconditionFailedException("Can only edit pending appointments")

        saveAppointment(appointmentDTO, id)
    }

    fun addAppointment(appointmentDTO: AppointmentDTO) {
        if (appointmentDTO.id != 0)
            throw PreconditionFailedException("Appointment id must be 0 in insertion or > 0 for update")

        saveAppointment(appointmentDTO)
    }

    @Cacheable("PetAppointments")
    fun getPetAppointments(petId: Int) =
        petService.getPetWithAppointments(petId).appointments.map { it.toDTO() }

    fun changeVeterinarianAppointmentStatus(appointmentDTO: AppointmentDTO) {
        val savedAppoint = getAppointmentEntityById(appointmentDTO.id)

        val newStatus = AppointmentStatus.values()[appointmentDTO.status]
        if (newStatus == savedAppoint.status)
            throw PreconditionFailedException("Appointment already ${newStatus.name}")

        when (newStatus) {
            AppointmentStatus.Refused -> refuseAppointment(appointmentDTO)
            AppointmentStatus.Completed -> completeAppointment(appointmentStartDate(appointmentDTO), appointmentDTO.id)
            AppointmentStatus.Accepted -> acceptAppointment(appointmentDTO)
            else -> throw PreconditionFailedException()
        }

        saveAppointment(appointmentDTO, appointmentDTO.id)
    }

    private fun refuseAppointment(appointmentDTO: AppointmentDTO) {
        if (appointmentStartDate(appointmentDTO).before(now())) {
            throw PreconditionFailedException("Appointment ${appointmentDTO.id} Already passed")
        }

        if (appointmentDTO.justification.isEmpty())
            throw PreconditionFailedException("A justification is needed")
    }

    private fun acceptAppointment(appointmentDTO: AppointmentDTO) {
        if (appointmentStartDate(appointmentDTO).before(now())) {
            throw PreconditionFailedException("Appointment ${appointmentDTO.id} Already passed")
        }

        val schedule = scheduleService.getScheduleEntityByVetIdAndMonth(
            appointmentDTO.veterinarian,
            appointmentDTO.year,
            appointmentDTO.month
        ).get()
        val timeSlot = TimeSlot(appointmentDTO.startTime, appointmentDTO.endTime)
        schedule.bookAppointment(timeSlot)
        scheduleService.saveSchedule(schedule)
    }

    private fun appointmentStartDate(appointmentDTO: AppointmentDTO): Date {
        val startTime = appointmentDTO.startTime + 24
        val calendar = Calendar.getInstance()
        calendar.set(
            appointmentDTO.year,
            appointmentDTO.month - 1,
            startTime / 24,
            startTime % 24, 0
        )

        return calendar.time
    }

    private fun completeAppointment(startTime: Date, id: Int) {
        if (startTime.after(now())) {
            throw PreconditionFailedException("Appointment with $id didn't happen yet")
        }
    }
}
