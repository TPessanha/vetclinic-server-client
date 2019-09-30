package personal.ciai.vetclinic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VetclinicServerApplication

fun main(args: Array<String>) {
    runApplication<VetclinicServerApplication>(*args)
}
