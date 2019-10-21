package personal.ciai.vetclinic.service

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.UnsupportedMediaTypeException
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.model.User

@Service
class ImageService(
    @Autowired
    private val configurationProperties: ConfigurationProperties,
    @Autowired
    val cacheManager: CacheManager
) {
    companion object MediaTypes {
        val imageTypes = listOf(
            MediaType.IMAGE_JPEG.toString(),
            MediaType.IMAGE_PNG.toString()
        )
    }

    fun updatePetPhoto(pet: Pet, photo: MultipartFile): Pet {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val path = Paths.get(
            configurationProperties.fullPathToPetPhotos, "${pet.id}.jpg"
        )

        val directory = File(configurationProperties.fullPathToPetPhotos)
        if (!directory.exists())
            directory.mkdirs()
        Files.write(path, photo.bytes)

        pet.photo = path.toUri()

        // Delete cache
        val test = cacheManager.getCache("PetPicture")

        return pet
    }


    fun getPetPhoto(pet: Pet): ByteArray {
        val photoURI = pet.photo
        if (photoURI == null)
            throw NotFoundException("Pet does not have a profile photo")
        else
            return File(photoURI).readBytes()
    }

    /*** Users ***/

    fun updateUserPhoto(user: User, photo: MultipartFile): User {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val path = Paths.get(
            configurationProperties.fullPathToUserPhotos, "${user.id}.jpg"
        )

        val directory = File(configurationProperties.fullPathToUserPhotos)
        if (!directory.exists())
            directory.mkdirs()
        Files.write(path, photo.bytes)

        user.photo = path.toUri()

        return user // TODO set owner
    }

    fun getUserPhoto(user: User): ByteArray {
        val photoURI = user.photo
        if (photoURI == null)
            throw NotFoundException("User does not have a profile photo")
        else
            return File(photoURI).readBytes()
    }

    /*** Clients ***/

    fun updateClientPhoto(client: Client, photo: MultipartFile): Client {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val path = Paths.get(
            configurationProperties.fullPathToUserPhotos, "${client.id}.jpg"
        )
        println("PATH: $path")
        println("Uri: ${path.toUri()}")

        val directory = File(configurationProperties.fullPathToUserPhotos)
        if (!directory.exists())
            directory.mkdirs()
        Files.write(path, photo.bytes)

        client.photo = path.toUri()

        return client // TODO set owner
    }

    fun getClientPhoto(client: Client): ByteArray {
        val photoURI = client.photo
        if (photoURI == null)
            throw NotFoundException("User does not have a profile photo")
        else
            return File(photoURI).readBytes()
    }
}
