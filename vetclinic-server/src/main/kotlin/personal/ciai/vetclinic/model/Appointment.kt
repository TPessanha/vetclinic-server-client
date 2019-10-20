package personal.ciai.vetclinic.model

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import personal.ciai.vetclinic.dto.AppointmentDTO

@Entity
@Table(name = "appointments")
class Appointment(
    id: Int,
    @Embedded
    var timeSlot: TimeSlot,
//    @Column(nullable = false)
//    val veterinarian: Pet,
    @ManyToOne
    @JoinColumn(name = "pet_id")
    var pet: Pet,
    @ManyToOne
    @JoinColumn(name = "client_id")
    var client: Client,
    @Column(nullable = false)
    var description: String = "",
    @Enumerated(EnumType.ORDINAL)
    var status: AppointmentStatus = AppointmentStatus.Pending
) : IdentifiedBaseEntity(id) {

    override fun toDTO() = AppointmentDTO(
        id = id,
        startTime = this.timeSlot.startDate.time,
        endTime = this.timeSlot.endDate.time,
//        veterinarian = veterinarian.id.toString(),
        pet = pet.id,
        client = client.id,
        description = description,
        status = status.ordinal
    )
}
