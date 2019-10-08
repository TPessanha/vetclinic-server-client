package personal.ciai.vetclinic.service

import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@Service
class PetService(
    @Autowired
    val repository: PetRepository,
    private val configurationProperties: ConfigurationProperties

) {
    fun getPetById(id: Int) = getPetEntityById(id).toDTO()

    private fun getPetEntityById(id: Int): Pet {
        val pet = repository.findById(id)
        if (pet.isPresent)
            return pet.get()
        else
            throw NotFoundException("Pet with id ($id) not found")
    }

    fun savePet(petDTO: PetDTO) {
        val pet = Pet(
            id = petDTO.id,
            species = petDTO.species,
            age = petDTO.age,
            photo = if (petDTO.photo == null) null else URI.create(petDTO.photo)
        )
        repository.save(pet)
    }

    fun findAllPets(): List<PetDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    fun isPetPresent(id: Int): Boolean {
        return repository.findByIdOrNull(id) != null
    }

    private fun getExtension(fileName: String?): String {
        if (fileName == null)
            throw IllegalArgumentException("fileName can't be null")
        return fileName.substring(fileName.lastIndexOf('.'))
    }

    fun updatePhoto(id: Int, filePicture: MultipartFile): URI {
        val pet = getPetById(id)
        val path = Paths.get(
            configurationProperties.fullPathToPetPhotos, "$id.jpg"
        )

        println("PATH: $path")
        println("PATHtoUri: ${path.toUri()}")

        val updatedPet = pet.copy(photo = path.toUri().toString())
        File(configurationProperties.fullPathToPetPhotos).mkdirs()
        Files.write(path, filePicture.bytes)
        savePet(updatedPet)
        return path.toUri()
    }

    fun getPhoto(id: Int): File {
        val pet = getPetEntityById(id)
        val photoURI = pet.photo
        return if (photoURI == null)
            throw NotFoundException("Pet does not have a profile picture")
        else
            File(photoURI)
    }
}
