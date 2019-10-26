package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModelProperty
import personal.ciai.vetclinic.model.ScheduleStatus
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.model.Veterinarian

/**
 * Models a Schedule DTO.
 *
 * @property id the identification of the Schedule.
 * @property date the date of the Schedule.
 * @property startTime the start time for date of the Schedule.
 * @property endTime the end time for date of the Schedule.
 * @property status the status of the Schedule.
 * @property veterinarian the veterinarian of the Schedule.
 * @constructor Creates a Schedule DTO.
 */
data class SchedulesDTO(
    @ApiModelProperty(
        "An unique identifier for the schedule",
        required = false,
        readOnly = false,
        example = "1"
    )
    val id: Int = -1,

    @ApiModelProperty(
        "The time that start schedule start",
        required = true,
        readOnly = false,
        example = "1"
    )
    val startTime: Long,
    @ApiModelProperty(
        "The time that the schedule ends",
        required = true,
        readOnly = false,
        example = "1"
    )
    val endTime: Long,

    @ApiModelProperty(
        "The veterinarian id",
        required = true,
        readOnly = false,
        example = "1"
    )
    var vetId: Int,

    @ApiModelProperty(
        "The status of schedule",
        required = true,
        readOnly = false,
        example = "1"
    )
    val status: ScheduleStatus

) : Transferable {
    fun toEntity(vet: Veterinarian) = toEntity(this.id, vet)

    fun toEntity(newId: Int, vet: Veterinarian) =
        Schedules(
            id = newId,
            timeSlot = TimeSlot(startTime, endTime),
            status = status,
            veterinarian = vet
        )
}
