package personal.ciai.vetclinic

import java.net.URI
import java.time.YearMonth
import java.util.BitSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.event.EventListener
import org.springframework.core.io.ResourceLoader
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.AppointmentStatus
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.model.Role
import personal.ciai.vetclinic.model.Role.RoleName
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.repository.AdministratorRepository
import personal.ciai.vetclinic.repository.AppointmentRepository
import personal.ciai.vetclinic.repository.ClientRepository
import personal.ciai.vetclinic.repository.PetRepository
import personal.ciai.vetclinic.repository.RoleRepository
import personal.ciai.vetclinic.repository.ScheduleRepository
import personal.ciai.vetclinic.repository.VeterinarianRepository
import personal.ciai.vetclinic.service.ImageService

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
    val appointmentRepository: AppointmentRepository,
    @Autowired
    val roleRepository: RoleRepository,
    @Autowired
    val scheduleRepository: ScheduleRepository,
    @Autowired
    val imageService: ImageService,
    @Autowired
    val resourceLoader: ResourceLoader
) {
    @EventListener
    fun appReady(event: ApplicationReadyEvent) {
        val debugAndTesting = true

        val roles = addRoles()
        addAdmin(roles)
        if (debugAndTesting) {
            val clients = addClients(roles)
            val pet = addPets(clients)
            val vet = addVets(roles)
            addAppointments(vet, clients[0], pet)
        }
    }

    private fun addAppointments(vet: Veterinarian, client: Client, pet: Pet) {
        val app = Appointment(1, 10, 2019, TimeSlot(2, 3), vet, pet, client, "Testing 1", AppointmentStatus.Pending)
        val app2 = Appointment(2, 10, 2019, TimeSlot(5, 6), vet, pet, client, "Testing 2", AppointmentStatus.Pending)

        appointmentRepository.save(app)
        appointmentRepository.save(app2)
    }

    private fun addVets(roles: List<Role>): Veterinarian {
        val vet1 =
            Veterinarian(
                0,
                "vet@gmail.com",
                "Veterio",
                912342323,
                "vet1",
                BCryptPasswordEncoder().encode("password"),
                "Rua de tras",
                URI("empty"),
                true,
                employeeId = 2
            )

        vet1.roles.add(roles[1])
        vet1.roles.add(roles[2])
        veterinarianRepository.save(vet1)

        var blocks = BitSet(720)
        var booked = BitSet(720)
        for (i in 0 until 720) {
            blocks[i] = false
            booked[i] = false
        }
        for (i in 0 until 5)
            blocks[i] = true

        val arr = blocks.toByteArray()
        val arr2 = booked.toByteArray()
        val sche = Schedule(0, YearMonth.of(2019, 10), vet1, arr, arr2)
        val sche2 = Schedule(0, YearMonth.of(2019, 11), vet1, arr, arr2)

        scheduleRepository.save(sche)
        scheduleRepository.save(sche2)

        return vet1
    }

    private fun addPets(clients: List<Client>): Pet {
        val p1 = Pet(
            id = 0, species = "cat", age = 2, owner = clients[0]
        )
        val p2 = Pet(
            id = 0, species = "dog", age = 3, owner = clients[0]
        )
        val p3 = Pet(
            id = 0, species = "pig", age = 6, owner = clients[1]
        )

        petRepository.save(p1)
        petRepository.save(p2)
        petRepository.save(p3)

        return p1
    }

    private fun addClients(roles: List<Role>): List<Client> {
        val c1 = Client(
            0,
            "rui@gmail.com",
            "rui",
            925364545,
            "user2",
            BCryptPasswordEncoder().encode("password"),
            "Rua da direita"
        )

        val c2 = Client(
            0,
            "pedro@gmail.com",
            "pedro",
            925354545,
            "user3",
            BCryptPasswordEncoder().encode("password"),
            "Rua da esquerda"
        )

        c1.roles.add(roles[2])
        c2.roles.add(roles[2])

        clientRepository.save(c1)
        clientRepository.save(c2)

        return listOf(c1, c2)
    }

    private fun addAdmin(roles: List<Role>) {
        val defaultImageResource = resourceLoader.getResource(
            "classpath:static/profilePictures/DefaultProfilePicture.png"
        )

        val uri = imageService.unsafeUpdateUserPhoto(1, defaultImageResource.file)

        val admin = Administrator(
            id = 0,
            employeeId = 1,
            email = "admin@gmail.com",
            name = "adminion",
            phoneNumber = 911234567,
            username = "admin",
            password = BCryptPasswordEncoder().encode("password"),
            address = "Rua da direita",
            photo = uri
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
