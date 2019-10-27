package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModelProperty
import personal.ciai.vetclinic.model.Schedule
import personal.ciai.vetclinic.service.VeterinarianService

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
data class ScheduleDTO(
    @ApiModelProperty(
        "An unique identifier for the schedule",
        required = false,
        readOnly = false,
        example = "1"
    )
    val id: Int = -1,

    @ApiModelProperty(
        "The year of the schedule",
        required = true,
        readOnly = false,
        example = "2019"
    )
    val year: Int,
    @ApiModelProperty(
        "The month of the schedule",
        required = true,
        readOnly = false,
        example = "6"
    )
    val month: Int,

    @ApiModelProperty(
        "The veterinarian id",
        required = true,
        readOnly = false,
        example = "1"
    )
    var vetId: Int,

    @ApiModelProperty(
        "The available slots",
        required = true,
        readOnly = false,
        example = "1"
    )
    val availableBlocks: List<Byte>,

    @ApiModelProperty(
        "The booked slots",
        required = true,
        readOnly = false,
        example = "1"
    )
    val bookedBlocks: List<Byte>

) : Transferable {
    fun toEntity(veterinarianService: VeterinarianService) = toEntity(this.id, veterinarianService)

    fun toEntity(newId: Int, vet: VeterinarianService) =
        Schedule(
            id = newId,
            year = year,
            month = month,
            veterinarian = vet.getVeterinarianEntity(this.vetId),
            availableBlocks = availableBlocks.toByteArray(),
            bookedBlocks = bookedBlocks.toByteArray()
        )
}
