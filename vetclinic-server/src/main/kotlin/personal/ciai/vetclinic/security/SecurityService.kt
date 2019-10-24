package personal.ciai.vetclinic.security

import java.security.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import personal.ciai.vetclinic.service.PetService
import personal.ciai.vetclinic.service.UserService

@Component("SecurityService")
public class SecurityService
    (
    @Autowired
    val petService: PetService,
    @Autowired
    val userService: UserService

){
    public fun isPetOwner(principal: Principal, id: Int): Boolean {
        val pet = petService.getPetEntityById(id)
        return pet.owner.username == principal.name
    }

    public fun isPrincipalWithID(principal: Principal, id: Int): Boolean{
        val user = userService.getUserEntityById(id)
        return user.username==principal.name
    }
}
