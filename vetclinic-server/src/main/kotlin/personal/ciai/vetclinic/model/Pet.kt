package personal.ciai.vetclinic.model

import io.swagger.annotations.ApiModel
import personal.ciai.vetclinic.dto.PetDTO

@ApiModel("Pet model", description = "Used to model pets")
/**
 * Models a Pet.
 *
 * This class is an entity and models a Pet.
 *
 * @property id the id of the Pet.
 * @property name the name of the Pet.
 * @property species the species of this Pet.
 * @constructor Creates a Pet.
 */
class Pet(val id: Int, val name: String, val species: String) {

    fun toDTO() = PetDTO(
        id = id,
        name = name,
        species = species
    )
}
