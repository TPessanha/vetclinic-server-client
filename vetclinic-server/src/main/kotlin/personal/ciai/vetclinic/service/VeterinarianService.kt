package personal.ciai.vetclinic.service

import java.util.Calendar
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
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.util.now

@Service
class VeterinarianService(
    @Autowired val vetRepository: VeterinarianRepository,
    @Autowired private val appointmentRepository: AppointmentRepository,
    @Autowired private val configurationProperties: ConfigurationProperties,
    @Autowired val petService: PetService,
    @Autowired val clientService: ClientService,
    @Autowired val userService: UserService
) {
    fun existByUserName(userName: String) = userService.existsByUsername(userName)

    fun existsById(id: Int): Boolean = vetRepository.existsById(id)

    fun getAllVeterinarian(): List<VeterinarianDTO> = vetRepository.findAll()
        .map { it.toDTO() }

    fun getVeterinarianById(vetId: Int): VeterinarianDTO {
        return getVeterinarianEntity(vetId).toDTO()
    }

    fun getVeterinarianEntity(id: Int): Veterinarian = vetRepository.findById(id)
        .orElseThrow { NotFoundException("Veterinarian account with id '$id' not found") }

    fun save(vetDTO: VeterinarianDTO) {
        if (existByUserName(vetDTO.username).not()) {
            vetRepository.save(vetDTO.toEntity(configurationProperties.fullPathToUserPhotos))
        } else throw ConflictException("Account with username '${vetDTO.username}' already exist")
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




}
