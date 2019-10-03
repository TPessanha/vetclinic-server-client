package personal.ciai.vetclinic.model

import personal.ciai.vetclinic.dto.PetDTO

/**
 * Models a Pet Entity.
 *
 * @property id the id of the Pet.
 * @property name the name of the Pet.
 * @property species the species of this Pet.
 * @constructor Creates a Pet.
 */
class Pet(
    id: Int,
    val name: String,
    val species: String,
    val owner: Client,
    val appointments: Nothing = TODO("Not Implemented"),
    val physicalDescription: String,
    val notes: String,
    val image: ProfileImage
) : Entity<PetDTO>(id) {

    override fun toDTO() = PetDTO(
        id = id,
        name = name,
        species = species
    )
}
