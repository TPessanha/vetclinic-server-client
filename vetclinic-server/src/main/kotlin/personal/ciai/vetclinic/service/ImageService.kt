package personal.ciai.vetclinic.service

import java.io.File
import java.lang.Exception
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.exception.ServerErrorException
import personal.ciai.vetclinic.exception.UnsupportedMediaTypeException

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
            configurationProperties.fullPathToPetPhotos, "$petId.jpg"
        )

        writeToFileSystem(path, photo.bytes, File(configurationProperties.fullPathToPetPhotos))

        return path.toUri()
    }

    fun getPetPhoto(photoURI: URI?): ByteArray {
        if (photoURI == null)
            throw NotFoundException("Pet does not have a profile photo")
        else
            return readFromFileSystem(File(photoURI))
    }

    /*** Users ***/

    fun updateUserPhoto(userId: Int, photo: MultipartFile): URI {
        if (photo.contentType !in imageTypes)
            throw UnsupportedMediaTypeException("Photos can only be of type (jpg/png)")

        val path = Paths.get(
            configurationProperties.fullPathToUserPhotos, "$userId.jpg"
        )

        writeToFileSystem(path, photo.bytes, File(configurationProperties.fullPathToUserPhotos))

        return path.toUri() // TODO set owner
    }

    fun unsafeUpdateUserPhoto(userId: Int, photo: File): URI {
        val path = Paths.get(
            configurationProperties.fullPathToUserPhotos, "$userId.jpg"
        )

        writeToFileSystem(path, photo.readBytes(), File(configurationProperties.fullPathToUserPhotos))

        return path.toUri() // TODO set owner
    }

    fun getUserPhoto(photoURI: URI?): ByteArray {
        if (photoURI == null)
            throw NotFoundException("User does not have a profile photo")
        else
            return readFromFileSystem(File(photoURI))
    }

    private fun writeToFileSystem(path: Path, bytes: ByteArray, directory: File) {
        try {
            if (!directory.exists())
                directory.mkdirs()

            Files.write(path, bytes)
        } catch (e: Exception) {
            throw ServerErrorException("An error occurred trying to save image to file system")
        }
    }

    private fun readFromFileSystem(file: File): ByteArray {
        try {
            return file.readBytes()
        } catch (e: Exception) {
            throw ServerErrorException("An error occurred trying to read image from file system")
        }
    }
}
