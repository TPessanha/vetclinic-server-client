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
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
//    var from: Date,
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
//    var to: Date
//

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "veterinarian_id")
    var veterinarian: Veterinarian,

    @Column(nullable = false)
    var status: ScheduleStatus
) : IdentifiedEntity(id) {

    override fun toDTO() = ScheduleDTO(
        id = id,
        veterinarian = veterinarian,
        date = date,
//        from = from,
//        to = to
        status = ScheduleStatus.Available
    )
}
