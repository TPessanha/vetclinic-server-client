package personal.ciai.vetclinic.config

import java.net.URI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.model.Administrative
import personal.ciai.vetclinic.repository.AdministrativeRepository

// import org.springframework.security.crypto.password.PasswordEncoder;

@Component
class SetupConfiguration(@Autowired private val administrativeRepository: AdministrativeRepository) :
    ApplicationListener<ContextRefreshedEvent> {
    var alreadySetup = false

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) {
            return
        }
        // Setup a default Administrative Account
        val defaultAdmin = Administrative(
            id = -1,
            employeeId = -1,
            name = "Veterinarian Clinic",
            username = "VetClinic",
            phoneNumber = 921321653,
            email = "vetClinic@vetclinic.pt",
            password = "admin",
            photo = URI.create("default"),
            address = "Rua da Caparica n 1, 2313-134 Lisbia"
        )
        createAdministrativeIfNotFound(defaultAdmin)

        alreadySetup = true
    }

    private fun createAdministrativeIfNotFound(admin: Administrative) {
        if (administrativeRepository.existsAdministrativeByEmail(admin.email).not())
            administrativeRepository.save(admin)
    }
}
