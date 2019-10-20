package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModelProperty
import java.sql.Time
import java.util.Date
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Veterinarian

/**
 * Models a Schedule DTO.
 *
 * @property id the identification of the Schedule.
 * @property date the date of the Schedule.
 * @property from the start time for date of the Schedule.
 * @property to the end time for date of the Schedule.
 * @property status the status of the Schedule.
 * @property veterinarian the veterinarian of the Schedule.
 * @constructor Creates a Schedule DTO.
 */
data class ScheduleDTO(
    @ApiModelProperty(
        "An unique identifier for the schedule",
        required = false,
        readOnly = false,
        example = "1"
    )
    val id: Int = -1,

    @ApiModelProperty(
        "The date scheduled for the Veterinarian",
        required = true,
        readOnly = false,
        example = "1"
    )
    var date: Date,

    @ApiModelProperty(
        "The time that start schedule start",
        required = true,
        readOnly = false,
        example = "1"
    )
    val from: Time,
    @ApiModelProperty(
        "The time that the schedule ends",
        required = true,
        readOnly = false,
        example = "1"
    )
    val to: Time,
    @ApiModelProperty(
        "The veterinarian",
        required = true,
        readOnly = false,
        example = "1"
    )
    var veterinarian: Veterinarian,

    @ApiModelProperty(
        "The status of schedule",
        required = true,
        readOnly = false,
        example = "1"
    )
    val status: ScheduleStatus

) : BaseDTO {
    fun toEntity() = toEntity(this.id)

    fun toEntity(newId: Int) =
        Schedule(
            id = newId,
            date = this.date,
            from = from,
            to = to,
            status = status,
            veterinarian = veterinarian
        )
}
