package personal.ciai.vetclinic.model

import java.util.Date
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Temporal
import javax.persistence.TemporalType
import org.springframework.format.annotation.DateTimeFormat
import personal.ciai.vetclinic.exception.PreconditionFailedException

@Embeddable
class TimeSlot(
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    val startDate: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    val endDate: Date
) {
    constructor(startDate: Long, endDate: Long) : this(Date(startDate), Date(endDate))

    init {
        if (endDate < startDate)
            throw PreconditionFailedException("Start date most be before end date")
    }

    /**
     * Checks if this TimeSlot conflicts with another TimeSlot
     *
     * @param timeSlot TimeSlot Another TimeSlot to check against
     * @return Boolean true if there is NO conflict, false if there is a conflict
     */
    fun checkConflict(timeSlot: TimeSlot) =
        (timeSlot.endDate <= this.startDate || timeSlot.startDate >= this.endDate)
}
