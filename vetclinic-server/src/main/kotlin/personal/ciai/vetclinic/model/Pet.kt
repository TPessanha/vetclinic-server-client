package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
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
    @ManyToOne
    var owner: Client,
    @OneToMany(mappedBy = "pet")
    var appointments: MutableList<Appointment> = arrayListOf(),
    @Column(nullable = false)
    var notes: String = "",
    @Column(nullable = false)
    var physicalDescription: String = "",
    @Column(nullable = false)
    var medicalRecord: String = "",
    @Column
    var photo: URI? = null
) : IdentifiedEntity(id) {

    override fun toDTO() = PetDTO(
        id = id,
        species = species,
        age = age,
        owner = owner.id,
//        appointments = appointments.map { it.toDTO() },
        notes = notes,
        physicalDescription = physicalDescription,
        medicalRecord = medicalRecord,
        photo = photo?.toString()
    )
}
