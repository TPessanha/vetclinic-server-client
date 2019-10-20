package personal.ciai.vetclinic.model

import java.sql.Time
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
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
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var date: Date,
    val from: Time,
    val to: Time,
    @JoinColumn(name = "veterinarian_id")
    var veterinarian: Veterinarian,
    @Column(nullable = false)
    val status: ScheduleStatus
) : IdentifiedEntity(id) {

    override fun toDTO() = ScheduleDTO(
        id = id,
        veterinarian = veterinarian,
        date = date,
        from = from,
        to = to,
        status = status
    )
}
