package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@Service
class PetService(
    @Autowired
    val repository: PetRepository,
    @Autowired
    val imageService: ImageService
) {
    fun getPetById(id: Int) = getPetEntityById(id).toDTO()

    fun getPetEntityById(id: Int): Pet =
        repository.findById(id).orElseThrow { NotFoundException("Pet with id ($id) not found") }

    fun savePet(petDTO: PetDTO, id: Int = 0) {
        if (id > 0 && !repository.existsById(petDTO.id))
            throw NotFoundException("Pet with id (${petDTO.id}) not found")

        if (id < 0 || (id == 0 && petDTO.id != 0))
            throw ExpectationFailedException("Id must be 0 in insertion or > 0 for update")

        repository.save(petDTO.toEntity())
    }

    fun getAllPets() = repository.findAll().map { it.toDTO() }

    fun deletePet(id: Int) = repository.deleteById(id)

    fun deletePet(pet: Pet) = repository.delete(pet)

    fun updatePhoto(id: Int, photo: MultipartFile) {
        val pet = getPetEntityById(id)
        val newPet = imageService.updatePetPhoto(pet, photo)
        savePet(newPet.toDTO(), newPet.id)
    }

    fun getPhoto(id: Int): ByteArray {
        val pet = getPetEntityById(id)
        return imageService.getPetPhoto(pet)
    }

    fun getPetAppointments(id: Int): List<Appointment> {
        val pet =
            repository.findByIdWithAppointment(id)
                .orElseThrow { NotFoundException("Pet with id ($id) not found") }
        return pet.appointments
    }
}
