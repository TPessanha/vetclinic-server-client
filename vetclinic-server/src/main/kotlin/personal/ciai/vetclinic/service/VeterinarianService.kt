package personal.ciai.vetclinic.service

import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.AppointmentStatus.Accepted
import personal.ciai.vetclinic.model.AppointmentStatus.Completed
import personal.ciai.vetclinic.model.AppointmentStatus.Refused
import personal.ciai.vetclinic.model.AppointmentStatus.valueOf
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.util.now

@Service
class VeterinarianService(
    @Autowired val vetRepository: VeterinarianRepository,
    @Autowired private val appointmentRepository: AppointmentRepository,
    @Autowired private val schedulesService: SchedulesService,
    @Autowired private val configurationProperties: ConfigurationProperties
) {
    fun existByUserName(userName: String) = vetRepository.existsByUsername(userName)

    fun existsById(id: Int): Boolean = vetRepository.existsById(id)

    fun getAllVeterinarian(): List<VeterinarianDTO> = vetRepository.findAll()
        .map { it.toDTO() }

    fun getVeterinarianById(vetId: Int): VeterinarianDTO {
        return getVeterinarianEntity(vetId).toDTO()
    }

    fun getVeterinarianEntity(id: Int): Veterinarian = vetRepository.findById(id)
        .orElseThrow { NotFoundException("Veterinarian account with Id $id not found") }

    fun save(vetDTO: VeterinarianDTO) {
        if (existByUserName(vetDTO.username).not()) {
            vetRepository.save(vetDTO.toEntity(configurationProperties.fullPathToUserPhotos))
        } else throw ConflictException("Veterinarian account with Id ${vetDTO.id} already exist")
    }

    fun update(vetDTO: VeterinarianDTO) {
        val vet: Veterinarian = getVeterinarianEntity(vetDTO.id)

        vetRepository.save(vetDTO.toEntity(vet, configurationProperties.fullPathToUserPhotos))
    }

    fun delete(id: Int) {
        val vet: Veterinarian = getVeterinarianEntity(id)

        vet.enabled = false
        vetRepository.save(vet)
    }

    fun getVeterinarianAppointments(vetId: Int): List<AppointmentDTO> {
        return getVeterinarianEntity(vetId).appointments.map { it.toDTO() }
    }

    fun changeVeterinarianAppointmentStatus(appointmentDTO: AppointmentDTO) {
        val savedAppoint = appointmentRepository.findById(appointmentDTO.id)
            .orElseThrow { NotFoundException("Appointment with id (${appointmentDTO.id}) not found") }

        val newStatus = valueOf(appointmentDTO.status.toString())
        if (newStatus == savedAppoint.status)
            throw PreconditionFailedException("Appointment Already ${newStatus.name}")

        when (newStatus) {
            Refused -> refuseAppointment(appointmentDTO)
            Completed -> completeAppointment(Date(appointmentDTO.startTime), appointmentDTO.id)
            Accepted -> acceptAppointment(appointmentDTO)
            else -> throw PreconditionFailedException()
        }

        // appointmentRepository.save(appointmentDTO.toEntity(savedAppoint))  TODO NEED TOENTITY WITH ENTITY PARM
    }

    private fun refuseAppointment(appointmentDTO: AppointmentDTO) {
        if (Date(appointmentDTO.startTime).before(now())) {
            throw PreconditionFailedException("Appointment ${appointmentDTO.id} Already passed")
        }
        //  appointmentDTO.Justification.notPresent TODO( Need Var )
        // throw PreconditionFailedException("A justifiaction is need")
        val schedule = schedulesService.getScheduleByVeterinarianIdAndStartTimeEntity(
            appointmentDTO.veterinarian,
            Date(appointmentDTO.startTime)
        )
        schedule.status = ScheduleStatus.Available
        schedulesService.saveSchedule(schedule)
    }

    private fun acceptAppointment(appointmentDTO: AppointmentDTO) {
        if (Date(appointmentDTO.startTime).before(now())) {
            throw PreconditionFailedException("Appointment ${appointmentDTO.id} Already passed")
        }

        val schedule = schedulesService.getScheduleByVeterinarianIdAndStartTimeEntity(
            appointmentDTO.veterinarian,
            Date(appointmentDTO.startTime)
        )
        schedule.status = ScheduleStatus.Booked
        schedulesService.saveSchedule(schedule)
    }

    private fun completeAppointment(startTime: Date, vetId: Int) {
        if (startTime.after(now())) {
            throw PreconditionFailedException("Appointment $vetId Didnt happen yet")
        }
    }
}
