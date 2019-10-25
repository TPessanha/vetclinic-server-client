package personal.ciai.vetclinic.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import personal.ciai.vetclinic.dto.SchedulesDTO

@Entity
@Table(
    name = "schedules",
    uniqueConstraints = [UniqueConstraint(columnNames = ["startDate", "veterinarian"])]
)
class Schedules(
    id: Int,

    @Embedded
    var timeSlot: TimeSlot,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "veterinarian")
    var veterinarian: Veterinarian,

    @Column(nullable = false)
    var status: ScheduleStatus
) : IdentifiedEntity(id) {

    override fun toDTO() = SchedulesDTO(
        id = id,
        vetId = veterinarian.id,
        startTime = timeSlot.startDate as Long,
        endTime = timeSlot.endDate as Long,
        status = ScheduleStatus.Available
    )
}
