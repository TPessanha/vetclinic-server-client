package personal.ciai.vetclinic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import personal.ciai.vetclinic.config.ConfigurationProperties

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(ConfigurationProperties::class)
class VetclinicServerApplication

fun main(args: Array<String>) {
    runApplication<VetclinicServerApplication>(*args)
}

// @Component
// class Init(
//    @Autowired
//    val petService: PetService
// ) {
//    @EventListener
//    fun appReady(event: ApplicationReadyEvent) {
//        val petDTO = PetDTO(
//            species = "Bulldog",
//            age = 4,
//            owner = "Client001"
//        )
//
//        petService.savePet(petDTO)
//    }
// }
