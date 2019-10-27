package personal.ciai.vetclinic.security

import java.security.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.service.AdministratorService
import personal.ciai.vetclinic.service.PetService
import personal.ciai.vetclinic.service.ScheduleService
import personal.ciai.vetclinic.service.UserService
import personal.ciai.vetclinic.service.VeterinarianService

@Service("SecurityService")
class SecurityService(
    @Autowired
    val petService: PetService,
    @Autowired
    val userService: UserService,
    @Autowired
    val administratorService: AdministratorService,
    @Autowired
    val veterinarianService: VeterinarianService,
    @Autowired
    val schedulesService: ScheduleService

) {
    fun isPetOwner(principal: Principal, id: Int): Boolean {
        val pet = petService.getPetEntityById(id)
        return pet.owner.username == principal.name
    }

//    public fun isPetOwner(principal: Principal, id: Int?): Boolean {
//        val pet = petService.getPetEntityById(1)
//        return pet.owner.username == principal.name
//    }

    fun isPrincipalWithID(principal: Principal, id: Int): Boolean {
        val user = userService.getUserEntityById(id)
        return user.username == principal.name
    }

    fun isVeterinarianAccountOwner(principal: Principal, vetId: Int): Boolean =
        principal.name == veterinarianService.getVeterinarianById(vetId).username

    fun isAdministratorAccountOwner(principal: Principal, adminId: Int): Boolean =
        principal.name == administratorService.getAdministratorById(adminId).username
}
