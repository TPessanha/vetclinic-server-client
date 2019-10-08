package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import personal.ciai.vetclinic.dto.PetDTO

/**
 * Models a Pet Entity
 *
 * @property id Int An unique identifier for the pet
 * @property species String The pet species
 * @property age Int The age of the pet
 * @property owner Client<ClientDTO> The owner of the pet
 * @property appointments MutableList<Appointment> A list of appointments scheduled for the pet
 * @property notes String Notes about the pet
 * @property physicalDescription String A physical description of the pet
 * @property medicalRecord String The pet's medical records
 * @property photo File A photo of the pet
 * @constructor Creates a Pet Entity.
 */
@Entity
@Table(name = "pets")
class Pet(
    id: Int,

    @Column(nullable = false)
    val species: String,

    @Column(nullable = false)
    var age: Int,
    // var owner: Client<ClientDTO>,
    // val appointments: MutableList<Appointment>,
    // var notes: String ,
    // var physicalDescription: String,
    // var medicalRecord: String =""
    @Column(nullable = true)
    var photo: URI? = null
) : IdentifiedEntity<PetDTO>(id) {

    override fun toDTO() = PetDTO(
        id = id,
        species = species,
        age = age,
        photo = photo?.toString()
    )
//        PetDTO(
//        id = id,
//        species = species,
//        age = age,
//        appointments = TODO("Appointment class is missing"),
//        notes = notes,
//        physicalDescription = physicalDescription,
//        medicalRecord = medicalRecord,
//        owner = owner.username,
//        photo = photo.name
//    )
}
