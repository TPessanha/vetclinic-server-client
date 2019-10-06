package personal.ciai.vetclinic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import personal.ciai.vetclinic.config.ConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(ConfigurationProperties::class)
class VetclinicServerApplication

fun main(args: Array<String>) {
    runApplication<VetclinicServerApplication>(*args)
}
