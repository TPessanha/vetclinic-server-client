package personal.ciai.vetclinic.model

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import personal.ciai.vetclinic.dto.AppointmentDTO

@Entity
@Table(name = "appointments")
class Appointment(
    id: Int,
    @Column(nullable = false)
    val date: Date,
//    @Column(nullable = false)
//    val veterinarian: Pet,
    @ManyToOne
    @JoinColumn(name = "Pet_id")
    val pet: Pet,
//    @Column(nullable = false)
//    val client: Pet,
    @Column(nullable = false)
    val description: String
) : IdentifiedEntity<AppointmentDTO>(id) {

    override fun toDTO() = AppointmentDTO(
        id = id,
        date = date.time,
//        veterinarian = veterinarian.id.toString(),
        pet = pet.id,
//        client = client.id.toString(),
        description = description
    )
}
