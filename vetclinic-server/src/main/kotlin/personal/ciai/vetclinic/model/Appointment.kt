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
    var month: Int,
    var year: Int,

    @Embedded
    var timeSlot: TimeSlot,

    @ManyToOne
    @JoinColumn(name = "vet_id")
    val veterinarian: Veterinarian,

    @ManyToOne
    @JoinColumn(name = "pet_id")
    var pet: Pet,

    @ManyToOne
    @JoinColumn(name = "client_id")
    var client: Client,

    @Column(nullable = false)
    var description: String = "",

    @Enumerated(EnumType.ORDINAL)
    var status: AppointmentStatus = AppointmentStatus.Pending,

    var justification: String = ""
) : IdentifiedEntity(id) {

    override fun toDTO() = AppointmentDTO(
        id = id,
        month = this.month,
        year = this.year,
        startTime = this.timeSlot.startDate,
        endTime = this.timeSlot.endDate,
        veterinarian = veterinarian.id,
        pet = pet.id,
        client = client.id,
        description = description,
        status = status.ordinal,
        justification = justification
    )
}
