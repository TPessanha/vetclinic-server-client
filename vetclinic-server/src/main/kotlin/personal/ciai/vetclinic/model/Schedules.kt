package personal.ciai.vetclinic.model

import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import org.springframework.format.annotation.DateTimeFormat
import personal.ciai.vetclinic.dto.ScheduleDTO

@Entity
@Table(name = "schedules")
class Schedules(
    id: Int,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    var date: Date,

    @Embedded
    var timeSlot: TimeSlot,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "veterinarian_id")
    var veterinarian: Veterinarian,

    @Column(nullable = false)
    var status: ScheduleStatus
) : IdentifiedEntity(id) {

    override fun toDTO() = ScheduleDTO(
        id = id,
        vetId = veterinarian.id,
        date = date,
        startTime = timeSlot.endDate.time,
        endTime = timeSlot.endDate.time,
        status = ScheduleStatus.Available
    )
}
