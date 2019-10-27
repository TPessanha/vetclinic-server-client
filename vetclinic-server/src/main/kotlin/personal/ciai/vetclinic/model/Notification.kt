package personal.ciai.vetclinic.model

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.PrePersist
import javax.persistence.Table
import org.springframework.data.annotation.CreatedDate
import personal.ciai.vetclinic.dto.NotificationDTO

@Entity
@Table(name = "notification")
class Notification(
    id: Int,

    var message: String,

    @ManyToOne
    @JoinColumn(name = "client")
    var client: Client,

    @ManyToOne
    @JoinColumn(name = "appointment")
    var appointment: Appointment,

    var readed: Boolean = false

) : IdentifiedEntity(id) {
    @CreatedDate
    @Column(name = "created_date")
    var createdDate: Date? = null

    @PrePersist
    protected fun prePersist() {
        if (this.createdDate == null) {
            createdDate = Date()
        }
    }

    override fun toDTO() = NotificationDTO(
        id = id,
        message = message,
        isReaded = readed,
        clientId = client.id,
        timeCreate = createdDate!!.toInstant().epochSecond
    )

    companion object {

        fun createNotification(
            client: Client,
            appointment: Appointment,
            newStatus: AppointmentStatus
        ) = Notification(
            id = -1,
            message = newStatus.toString(),
            client = client,
            appointment = appointment
        )
    }
}
