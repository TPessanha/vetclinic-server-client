package personal.ciai.vetclinic.config

import java.net.URI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.repository.AdministratorRepository

// import org.springframework.security.crypto.password.PasswordEncoder;

@Component
class SetupConfiguration(@Autowired private val administrativeRepository: AdministratorRepository) :
    ApplicationListener<ContextRefreshedEvent> {
    var alreadySetup = false

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) {
            return
        }
        // Setup a default Administrator Account
        val defaultAdmin = Administrator(
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
        createAdministratorIfNotFound(defaultAdmin)

        alreadySetup = true
    }

    private fun createAdministratorIfNotFound(admin: Administrator) {
        if (administrativeRepository.existsAdministratorByEmail(admin.email).not())
            administrativeRepository.save(admin)
    }
}
