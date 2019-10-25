package personal.ciai.vetclinic.service

import io.swagger.models.auth.In
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.UnsupportedMediaTypeException
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.model.User
import java.net.URI

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

    fun updatePetPhoto(petId: Int, photo: MultipartFile): URI {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val path = Paths.get(
            configurationProperties.fullPathToPetPhotos, "${petId}.jpg"
        )

        val directory = File(configurationProperties.fullPathToPetPhotos)
        if (!directory.exists())
            directory.mkdirs()
        Files.write(path, photo.bytes)

        return path.toUri()
    }

    fun getPetPhoto(photoURI: URI?): ByteArray {
        if (photoURI == null)
            throw NotFoundException("Pet does not have a profile photo")
        else
            return File(photoURI).readBytes()
    }

    /*** Users ***/

    fun updateUserPhoto(userId: Int, photo: MultipartFile): URI {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val path = Paths.get(
            configurationProperties.fullPathToUserPhotos, "${userId}.jpg"
        )

        val directory = File(configurationProperties.fullPathToUserPhotos)
        if (!directory.exists())
            directory.mkdirs()
        Files.write(path, photo.bytes)

        return path.toUri() // TODO set owner
    }

    fun getUserPhoto(photoURI: URI?): ByteArray {
        if (photoURI == null)
            throw NotFoundException("User does not have a profile photo")
        else
            return File(photoURI).readBytes()
    }
}
