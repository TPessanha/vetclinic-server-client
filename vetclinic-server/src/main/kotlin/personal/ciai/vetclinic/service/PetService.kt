package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@Service
class PetService(
    @Autowired
    val repository: PetRepository,
    @Autowired
    val imageService: ImageService,
    @Autowired
    val clientService: ClientService,
    @Autowired
    val configurationProperties: ConfigurationProperties
) {
    fun getPetById(id: Int) = getPetEntityById(id).toDTO()

    fun getPetEntityById(id: Int): Pet =
        repository.findById(id).orElseThrow { NotFoundException("Pet with id ($id) not found") }

    private fun savePet(petDTO: PetDTO, id: Int = 0) {
        val newPet = petDTO.toEntity(id, configurationProperties.fullPathToPetPhotos, clientService)
        repository.save(newPet)
    }

    fun updatePet(petDTO: PetDTO, id: Int) {
        if (id <= 0 || !repository.existsById(id))
            throw NotFoundException("Pet with id ($id) not found")

        savePet(petDTO, id)
    }

    fun addPet(petDTO: PetDTO) {
        if (petDTO.id != 0)
            throw PreconditionFailedException("Pet id must be 0 in insertion")

        savePet(petDTO)
    }

    fun getAllPets() = repository.findAllByEnabled(true).map { it.toDTO() }

    fun getClientPets(clientId: Int): List<PetDTO> {
        val client = clientService.getClientWithPets(clientId)
        return client.pets.filter { it.enabled }.map { it.toDTO() } //should not have to filer but no time to fix query
    }

    fun deletePet(id: Int) {
        val pet = getPetEntityById(id)
        deletePet(pet)
    }

    fun deletePet(pet: Pet) {
        pet.enabled=false
        val saved = repository.save(pet)
    }

    @CacheEvict("PetPicture", key = "#id")
    fun updatePhoto(id: Int, photo: MultipartFile) {
        val uri = imageService.updatePetPhoto(id, photo)
        val newPet = getPetEntityById(id)
        newPet.photo = uri
        repository.save(newPet)
    }

    @Cacheable("PetPicture", key = "#id")
    fun getPhoto(id: Int): ByteArray {
        val pet = getPetEntityById(id)
        return imageService.getPetPhoto(pet.photo)
    }

    fun getPetWithAppointments(id: Int): Pet =
        repository.findByIdWithAppointments(id)
            .orElseThrow { NotFoundException("Pet with id ($id) not found") }
}
