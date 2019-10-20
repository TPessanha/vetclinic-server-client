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
    val petService: PetService,
    @Autowired
    val clientService: ClientService
) {
    fun getAllAppointments() = repository.findAll().map { it.toDTO() }

    fun getAppointmentById(id: Int) = getAppointmentEntityById(id).toDTO()

    fun getAppointmentEntityById(id: Int): Appointment =
        repository.findById(id).orElseThrow { NotFoundException("Appointment with id ($id) not found") }

    private fun saveAppointment(appointmentDTO: AppointmentDTO, id: Int = 0) {
        val newAppointment = appointmentDTO.toEntity(id, petService, clientService)
        repository.save(newAppointment)
    }

    fun updateAppointment(appointmentDTO: AppointmentDTO, id: Int) {
        if (id <= 0 || !repository.existsById(id))
            throw NotFoundException("Appointment with id ($id) not found")

        // TODO CHECK IF VET IS AVAILABLE
        // SOmething like vetService.CheckAvailability(AppointmentDTO)

        saveAppointment(appointmentDTO, id)
    }

    fun addAppointment(appointmentDTO: AppointmentDTO) {
        if (appointmentDTO.id != 0)
            throw ExpectationFailedException("Appointment id must be 0 in insertion or > 0 for update")

        // TODO CHECK IF VET IS AVAILABLE
        // SOmething like vetService.CheckAvailability(AppointmentDTO)

        saveAppointment(appointmentDTO)
    }

    // TODO Spring security check if is the right client
    fun getPetAppointments(petId: Int) =
        petService.getPetWithAppointments(petId).appointments.map { it.toDTO() }
}
