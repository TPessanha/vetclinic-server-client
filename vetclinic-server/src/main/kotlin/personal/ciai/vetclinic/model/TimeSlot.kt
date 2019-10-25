package personal.ciai.vetclinic.model

import javax.persistence.Column
import javax.persistence.Embeddable
import personal.ciai.vetclinic.exception.PreconditionFailedException

@Embeddable
class TimeSlot(
    @Column
    val startDate: Number,

    @Column
    val endDate: Number
) {
//    constructor(startDate: Long, endDate: Long) : this(Int.t, Date(endDate))

    init {
        if (endDate.toInt() < startDate.toInt())
            throw PreconditionFailedException("Start date most be before end date")
    }

    /**
     * Checks if this TimeSlot conflicts with another TimeSlot
     *
     * @param timeSlot TimeSlot Another TimeSlot to check against
     * @return Boolean true if there is NO conflict, false if there is a conflict
     */
    fun checkConflict(timeSlot: TimeSlot) =
        (timeSlot.endDate.toInt() < this.startDate.toInt() || timeSlot.startDate.toInt() > this.endDate.toInt())
}
