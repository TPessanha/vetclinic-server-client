package personal.ciai.vetclinic.service

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.PetDTO
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
        val imageTypes = listOf(
            MediaType.IMAGE_JPEG.toString(),
            MediaType.IMAGE_PNG.toString()
        )
    }

    fun getPetById(id: Int) = getPetEntityById(id).toDTO()

    fun getPetEntityById(id: Int): Pet =
        repository.findById(id).orElseThrow { NotFoundException("Pet with id ($id) not found") }

    fun savePet(petDTO: PetDTO, id: Int = -1) {
        if (id > 0 && !repository.existsById(petDTO.id))
            throw NotFoundException("Pet with id (${petDTO.id}) not found")

        repository.save(petDTO.copy(id = id).toEntity())
    }

    fun getAllPets() = repository.findAll().map { it.toDTO() }

    fun updatePhoto(id: Int, photo: MultipartFile): String {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val pet = getPetById(id)

        val path = Paths.get(
            configurationProperties.fullPathToPetPhotos, "$id.jpg"
        )
        println("PATH: $path")
        println("Uri: ${path.toUri()}")

        val directory = File(configurationProperties.fullPathToPetPhotos)
        if (!directory.exists())
            directory.mkdirs()
        Files.write(path, photo.bytes)

        val updatedPet = pet.copy(photo = path.toUri().toString())
        savePet(updatedPet, id)

        return "http://localhost:8080/pets/$id/photo"
    }

    fun getPhoto(id: Int): ByteArray {
        val pet = getPetEntityById(id)
        val photoURI = pet.photo
        return if (photoURI == null)
            throw NotFoundException("Pet does not have a profile photo")
        else
            File(photoURI).readBytes()
    }

    fun deletePet(id: Int) = repository.deleteById(id)

    fun deletePet(pet: Pet) = repository.delete(pet)
}
