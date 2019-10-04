package personal.ciai.vetclinic.model

import org.springframework.core.io.DefaultResourceLoader
import personal.ciai.vetclinic.dto.PetDTO
import java.io.File

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
    val species: String,
    var age: Int,
    val appointments: MutableList<Appointment>,
    var notes: String ,
    var physicalDescription: String,
    var medicalRecord: String ="",
    var owner: Client<ClientDTO>,
    var picture: File = DefaultResourceLoader().getResource("static/profilePictures/PetDefaultPicture.png").file
) : IdentifiedEntity<PetDTO>(id) {

    override fun toDTO() = PetDTO(
        id = id,
        species = species,
        age = age,
        appointments = TODO("Appointment class is missing"),
        notes = notes,
        physicalDescription = physicalDescription,
        medicalRecord = medicalRecord,
        owner = owner.username,
        picture = picture.name
    )
}
