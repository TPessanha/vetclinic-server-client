package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.AppointmentRepository
import java.util.Date

@Service
class AppointmentService(
    @Autowired
    val repository: AppointmentRepository,
    @Autowired
    val petService: PetService
) {
    fun findAllAppointments(): List<AppointmentDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    fun getAppointmentById(id: Int) = getPetEntityById(id).toDTO()

    fun getPetEntityById(id: Int): Appointment {
        val appointment = repository.findById(id)
        if (appointment.isPresent)
            return appointment.get()
        else
            throw NotFoundException("Appointment with id ($id) not found")
    }

    fun saveAppointment(appointmentDTO: AppointmentDTO, update: Boolean = false) {
        if (update && !repository.existsById(appointmentDTO.id))
            throw NotFoundException("Appointment with id (${appointmentDTO.id}) not found")

        repository.save(appointmentDTO.toEntity())
    }


    fun AppointmentDTO.toEntity(): Appointment {
        return Appointment(
            id = id,
            date = Date(date),
//            veterinarian = debugvet,
            description = description,
//            client = debugClient,
            pet = petService.getPetEntityById(this.pet)
        )
    }
}
