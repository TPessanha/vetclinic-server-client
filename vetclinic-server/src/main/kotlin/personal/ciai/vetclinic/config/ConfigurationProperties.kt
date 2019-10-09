package personal.ciai.vetclinic.config

import java.nio.file.Paths
import net.harawata.appdirs.AppDirsFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("vetclinic")
class ConfigurationProperties {
    lateinit var pathToPhotos: String
    lateinit var author: String
    lateinit var name: String

    val fullPathToPetPhotos: String by lazy {
        val appDir = AppDirsFactory.getInstance().getUserDataDir(name, null, author)
        Paths.get(appDir, pathToPhotos, "pets").toString()
    }
    val fullPathToUserPhotos: String by lazy {
        val appDir = AppDirsFactory.getInstance().getUserDataDir(name, null, author)
        Paths.get(appDir, pathToPhotos, "users").toString()
    }
}
