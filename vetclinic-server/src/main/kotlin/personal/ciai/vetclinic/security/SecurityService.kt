package personal.ciai.vetclinic.security

import java.security.Principal
import org.springframework.beans.factory.annotation.Autowired
import personal.ciai.vetclinic.service.PetService

public class SecurityService {
    @Autowired
    lateinit var petService: PetService

    public fun isPetOwner(principal: Principal, id: Int): Boolean {
        val pet = petService.getPetEntityById(id)
        return pet.owner.username == principal.name
    }
}
