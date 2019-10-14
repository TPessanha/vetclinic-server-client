package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Appointment DTO model", description = "Used to model appointments")
data class AppointmentDTO(
    @ApiModelProperty(
        "An unique identifier for the appointment",
        required = true,
        readOnly = false,
        example = "1"
    )
    val id: Int,
    @ApiModelProperty(
        "The date of the appointment",
        required = true,
        readOnly = false,
        example = "1539555316323"
    )
    val date: Long,
    // val veterinarian: String,
    @ApiModelProperty(
        "The pet ID scheduled for the appointment",
        required = true,
        readOnly = false,
        example = "1"
    )
    val pet: Int,
    // val client: String,
    @ApiModelProperty(
        "A description of what the veterinarian did to the animal",
        required = true,
        readOnly = false,
        example = "Cured the animal"
    )
    val description: String
) : BaseDTO
