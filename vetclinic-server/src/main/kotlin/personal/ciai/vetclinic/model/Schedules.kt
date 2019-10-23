package personal.ciai.vetclinic.model

import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.UniqueConstraint
import org.springframework.format.annotation.DateTimeFormat
import personal.ciai.vetclinic.dto.SchedulesDTO

@Entity
@Table(
    name = "schedules",
    uniqueConstraints = [UniqueConstraint(columnNames = ["startDate", "veterinarian"])]
)
class Schedules(
    id: Int,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    val startDate: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    val endDate: Date,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "veterinarian")
    var veterinarian: Veterinarian,

    @Column(nullable = false)
    var status: ScheduleStatus
) : IdentifiedEntity(id) {

    override fun toDTO() = SchedulesDTO(
        id = id,
        vetId = veterinarian.id,
        startTime = endDate.time,
        endTime = endDate.time,
        status = ScheduleStatus.Available
    )
}
