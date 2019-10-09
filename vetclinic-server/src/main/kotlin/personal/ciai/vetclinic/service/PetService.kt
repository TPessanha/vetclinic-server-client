package personal.ciai.vetclinic.service

import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.UnsupportedMediaTypeException
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@Service
class PetService(
    @Autowired
    val repository: PetRepository,
    private val configurationProperties: ConfigurationProperties

) {
    companion object MediaTypes {
        val imageTypes = listOf<String>(
            MediaType.IMAGE_JPEG.toString(),
            MediaType.IMAGE_PNG.toString()
        )
    }

    fun getPetById(id: Int) = getPetEntityById(id).toDTO()

    private fun getPetEntityById(id: Int): Pet {
        val pet = repository.findById(id)
        if (pet.isPresent)
            return pet.get()
        else
            throw NotFoundException("Pet with id ($id) not found")
    }

    fun savePet(petDTO: PetDTO, update: Boolean = false) {
        val exists = repository.existsById(petDTO.id)
        if (!update && exists)
            throw ConflictException("Pet with that ID already exists")
        if (update && !exists)
            throw NotFoundException("Pet with id (${petDTO.id}) not found")

        val pet = Pet(
            id = petDTO.id,
            species = petDTO.species,
            age = petDTO.age,
            medicalRecord = petDTO.medicalRecord,
            physicalDescription = petDTO.physicalDescription,
            notes = petDTO.notes,
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

    fun updatePhoto(id: Int, photo: MultipartFile): URI {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val pet = getPetById(id)

        val path = Paths.get(
            configurationProperties.fullPathToPetPhotos, "$id.jpg"
        )
        println("PATH: $path")
        println("Uri: ${path.toUri()}")

        val updatedPet = pet.copy(photo = path.toUri().toString())
        File(configurationProperties.fullPathToPetPhotos).mkdirs()
        Files.write(path, photo.bytes)
        savePet(updatedPet, update = true)
        return path.toUri()
    }

    fun getPhoto(id: Int): ByteArray {
        val pet = getPetEntityById(id)
        val photoURI = pet.photo
        return if (photoURI == null)
            throw NotFoundException("Pet does not have a profile photo")
        else
            File(photoURI).readBytes()
    }
}
