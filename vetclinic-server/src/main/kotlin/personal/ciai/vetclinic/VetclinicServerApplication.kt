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
import personal.ciai.vetclinic.model.Client
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
        addClients(roles)
    }

    private fun addClients(roles: List<Role>) {
        val c1 = Client(
            0,
            "rui@gmail.com",
            "Rui",
            925364545,
            "rui",
            BCryptPasswordEncoder().encode("password"),
            "Rua da direita"
        )

        c1.roles.add(roles[2])

        clientRepository.save(c1)
    }

    private fun addAdmin(roles: List<Role>) {
        val admin = Administrator(
            0,
            "admin@gmail.com",
            "adminion",
            911234567,
            "admin",
            BCryptPasswordEncoder().encode("password"),
            "Rua da direita",
            URI("emptyForDegub")
        )
        admin.roles.add(roles[0])
        admin.roles.add(roles[2])

        administratorRepository.save(admin)
    }

    private fun addRoles(): List<Role> {
        val admin = Role(1, RoleName.ROLE_ADMIN)
        val vet = Role(2, RoleName.ROLE_VET)
        val client = Role(3, RoleName.ROLE_CLIENT)

        roleRepository.save(admin)
        roleRepository.save(vet)
        roleRepository.save(client)

        return listOf(admin, vet, client)
    }
}
