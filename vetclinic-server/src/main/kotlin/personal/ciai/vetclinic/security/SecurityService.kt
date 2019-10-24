package personal.ciai.vetclinic.security

import java.security.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.service.AdministratorService
import personal.ciai.vetclinic.service.PetService
import personal.ciai.vetclinic.service.SchedulesService
import personal.ciai.vetclinic.service.VeterinarianService

@Component("SecurityService")
public class SecurityService {
    @Autowired
    lateinit var petService: PetService

    @Autowired
    lateinit var administratorService: AdministratorService

    @Autowired
    lateinit var veterinarianService: VeterinarianService

    @Autowired
    lateinit var schedulesService: SchedulesService

    public fun isPetOwner(principal: Principal, id: Int): Boolean {
        val pet = petService.getPetEntityById(id)
        return pet.owner.username == principal.name
    }

    private fun isVeterinarian(user: User): Boolean = veterinarianService.existsById(user.id)

    private fun isAdministrator(user: User): Boolean {
        return administratorService.existsById(user.id)
    }

    fun isVeterinarianAccountOwner(user: User, vetId: Int): Boolean = user.id == vetId && isVeterinarian(user)

    fun isAdministratorAccountOwner(user: User, adminId: Int): Boolean = user.id == adminId && isAdministrator(user)

    fun isScheduleVeterinarian(user: User, scheduleId: Int): Boolean =
        schedulesService.getOneScheduleById(scheduleId).vetId == user.id
}
