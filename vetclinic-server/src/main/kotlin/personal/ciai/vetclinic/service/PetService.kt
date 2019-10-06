package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@Service
class PetService(
    @Autowired
    val repository: PetRepository,
    private val configurationProperties: ConfigurationProperties

) {
    fun getPetById(id: Int) = repository.findByIdOrNull(id)?.toDTO()

    fun savePet(petDTO: PetDTO) {
        val pet = Pet(
            id = petDTO.id,
            species = petDTO.species,
            age = petDTO.age
        )
        repository.save(pet)
    }

    fun findAllPets(): List<PetDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    fun isPetPresent(id: Int): Boolean {
        return repository.findByIdOrNull(id) != null
    }
}
