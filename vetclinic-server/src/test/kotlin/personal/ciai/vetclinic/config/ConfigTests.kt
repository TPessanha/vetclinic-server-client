package personal.ciai.vetclinic.config

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ConfigTests(
    @Autowired
    val configurationProperties: ConfigurationProperties
) {
    @Test
    fun `Print all configs do manual check`() {
        println("FullPathToPetPictures: ${configurationProperties.fullPathToPetPhotos}")
        println("FullPathToUserPictures: ${configurationProperties.fullPathToUserPhotos}")
    }
}
