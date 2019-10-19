package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.repository.AppointmentRepository

@Service
class AppointmentService(
    @Autowired
    val repository: AppointmentRepository,
    @Autowired
    val petService: PetService
) {
    fun getAllAppointments() = repository.findAll().map { it.toDTO() }

    fun getAppointmentById(id: Int) = getAppointmentEntityById(id).toDTO()

    fun getAppointmentEntityById(id: Int): Appointment =
        repository.findById(id).orElseThrow { NotFoundException("Appointment with id ($id) not found") }

    fun saveAppointment(appointmentDTO: AppointmentDTO, id: Int = 0) {
        if (id > 0 && !repository.existsById(appointmentDTO.id)) {
            throw NotFoundException("Appointment with id (${appointmentDTO.id}) not found")
        }

        if (id < 0 || (id == 0 && appointmentDTO.id != 0))
            throw ExpectationFailedException("Id must be 0 in insertion or > 0 for update")

        repository.save(appointmentDTO.toEntity(petService))
    }

    fun getPetAppointments(petId: Int) =
        petService.getPetAppointments(petId).map { it.toDTO() }
}
