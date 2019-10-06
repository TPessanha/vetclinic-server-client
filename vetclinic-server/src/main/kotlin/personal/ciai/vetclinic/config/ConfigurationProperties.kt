package personal.ciai.vetclinic.config

import java.nio.file.Paths
import net.harawata.appdirs.AppDirsFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("vetclinic")
class ConfigurationProperties {
    lateinit var pathToPictures: String
    lateinit var author: String
    lateinit var name: String

    val fullPathToPetPictures: String by lazy {
        val appDir = AppDirsFactory.getInstance().getUserDataDir(name, null, author)
        Paths.get(appDir, pathToPictures, "pets").toString()
    }
    val fullPathToUserPictures: String by lazy {
        val appDir = AppDirsFactory.getInstance().getUserDataDir(name, null, author)
        Paths.get(appDir, pathToPictures, "users").toString()
    }
}
