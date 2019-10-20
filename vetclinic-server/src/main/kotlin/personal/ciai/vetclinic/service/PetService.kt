package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@Service
class PetService(
    @Autowired
    val repository: PetRepository,
    @Autowired
    val imageService: ImageService,
    @Autowired
    val clientService: ClientService
) {
    fun getPetById(id: Int) = getPetEntityById(id).toDTO()

    fun getPetEntityById(id: Int): Pet =
        repository.findById(id).orElseThrow { NotFoundException("Pet with id ($id) not found") }

    private fun savePet(petDTO: PetDTO, id: Int = 0) {
        val newPet = petDTO.toEntity(id, clientService)
        repository.save(newPet)
    }

    fun updatePet(petDTO: PetDTO, id: Int) {
        if (id > 0 && !repository.existsById(petDTO.id))
            throw NotFoundException("Pet with id ($id) not found")

        savePet(petDTO, id)
    }

    fun addPet(petDTO: PetDTO) {
        if (petDTO.id != 0)
            throw ExpectationFailedException("Pet id must be 0 in insertion or > 0 for update")

        savePet(petDTO)
    }

    fun getAllPets() = repository.findAll().map { it.toDTO() }

    fun getClientPets(clientId: Int): List<PetDTO> {
        val client = clientService.getClientWithPets(clientId)
        return client.pets.map { it.toDTO() }
    }

    fun deletePet(id: Int) = repository.deleteById(id)

    fun deletePet(pet: Pet) = repository.delete(pet)

    fun updatePhoto(id: Int, photo: MultipartFile) {
        val pet = getPetEntityById(id)
        val newPet = imageService.updatePetPhoto(pet, photo)
        updatePet(newPet.toDTO(), newPet.id)
    }

    fun getPhoto(id: Int): ByteArray {
        val pet = getPetEntityById(id)
        return imageService.getPetPhoto(pet)
    }

    fun getPetWithAppointments(id: Int): Pet =
        repository.findByIdWithAppointments(id)
            .orElseThrow { NotFoundException("Pet with id ($id) not found") }
}
