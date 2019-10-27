package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.VeterinarianRepository

@Service
class VeterinarianService(
    @Autowired val vetRepository: VeterinarianRepository,
    @Autowired private val configurationProperties: ConfigurationProperties,
    @Autowired val userService: UserService,
    @Autowired val imageService: ImageService
) {

    fun getAllVeterinarian(): List<VeterinarianDTO> = vetRepository.findAllByEnabled(true)
        .map { it.toDTO() }

    @Cacheable("Veterinarian", key = "#vetId")
    fun getVeterinarianById(vetId: Int): VeterinarianDTO {
        return getVeterinarianEntity(vetId).toDTO()
    }

    fun getVeterinarianEntity(id: Int): Veterinarian = vetRepository.findById(id)
        .orElseThrow { NotFoundException("Veterinarian account with id '$id' not found") }

    fun save(vetDTO: VeterinarianDTO) {
        if (userService.existsByUsername(vetDTO.username).not()) {
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

    fun getPhoto(id: Int): ByteArray {
        val vet = getVeterinarianEntity(id)
        return imageService.getUserPhoto(vet.photo)
    }

    fun updatePhoto(id: Int, photo: MultipartFile) {
        val vet = getVeterinarianEntity(id)
        vet.photo = imageService.updateUserPhoto(vet.id, photo)
        update(vet.toDTO())
    }
}
