package personal.ciai.vetclinic.security

import org.springframework.beans.factory.annotation.Autowired
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.service.PetService

public class SecurityService {
    @Autowired
    lateinit var petService: PetService

    public fun isPetOwner(user: User, id: Int): Boolean {
        val pet = petService.getPetEntityById(id)
        return pet.owner.id == user.id
    }
}
