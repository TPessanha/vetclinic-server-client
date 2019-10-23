package personal.ciai.vetclinic

import java.net.URI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.model.Role
import personal.ciai.vetclinic.model.RoleName
import personal.ciai.vetclinic.repository.AdministratorRepository
import personal.ciai.vetclinic.repository.ClientRepository
import personal.ciai.vetclinic.repository.PetRepository
import personal.ciai.vetclinic.repository.RoleRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(ConfigurationProperties::class)
class VetclinicServerApplication

fun main(args: Array<String>) {
    runApplication<VetclinicServerApplication>(*args)
}

@Component
class Init(
    @Autowired
    val petRepository: PetRepository,
    @Autowired
    val clientRepository: ClientRepository,
    @Autowired
    val administratorRepository: AdministratorRepository,
    @Autowired
    val veterinarianRepository: VeterinarianRepository,
    @Autowired
    val roleRepository: RoleRepository
) {
    @EventListener
    fun appReady(event: ApplicationReadyEvent) {
        val roles = addRoles()
        addAdmin(roles)
    }

    private fun addAdmin(roles: List<Role>) {
        val admin = Administrator(
            id = 0,
            employeeId = 0,
            email = "admin@gmail.com",
            name = "adminion",
            phoneNumber = 911234567,
            username = "admin",
            password = BCryptPasswordEncoder().encode("password"),
            address = "Rua da direita",
            photo = URI("emptyForDegub")
        )
        admin.roles.add(roles[0])
        admin.roles.add(roles[2])

        administratorRepository.save(admin)
    }

    private fun addRoles(): List<Role> {
        val admin = Role(1, RoleName.ADMIN)
        val vet = Role(2, RoleName.VET)
        val client = Role(3, RoleName.CLIENT)

        roleRepository.save(admin)
        roleRepository.save(vet)
        roleRepository.save(client)

        return listOf(admin, vet, client)
    }
}
