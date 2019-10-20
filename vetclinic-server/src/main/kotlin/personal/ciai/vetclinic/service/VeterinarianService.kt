package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.VeterinarianRepository

@Service
class VeterinarianService(@Autowired val vetRepository: VeterinarianRepository) {

    fun existsById(id: Int): Boolean = vetRepository.existsById(id)

    fun getAllVeterinarian(): List<VeterinarianDTO> = vetRepository.findAll()
        .map { it.toDTO() }

    fun getVeterinarianById(id: Int): VeterinarianDTO = getVeterinarianEntity(id).toDTO()

    private fun getVeterinarianEntity(id: Int): Veterinarian = vetRepository.findById(id)
        .orElseThrow { NotFoundException("Veterinarian account with Id $id not found") }

    fun save(vetDTO: VeterinarianDTO) {
        if (existsById(vetDTO.id).not()) {
            vetRepository.save(vetDTO.toEntity())
        } else throw ConflictException("Veterinarian account with Id ${vetDTO.id} already exist")
    }

    fun update(vetDTO: VeterinarianDTO, id: Int) {
        val vet: Veterinarian = getVeterinarianEntity(id)

        vetRepository.save(vetDTO.toEntity(vet))
    }

    fun delete(id: Int) {
        val vet: Veterinarian = getVeterinarianEntity(id)

        vet.enabled = false
        vetRepository.save(vet)
    }
}
