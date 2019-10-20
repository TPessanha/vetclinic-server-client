package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.AppointmentStatus
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.service.ClientService
import personal.ciai.vetclinic.service.PetService

@ApiModel("Appointment DTO model", description = "Used to model appointments")
data class AppointmentDTO(
    @ApiModelProperty(
        "An unique identifier for the appointment",
        required = true,
        readOnly = false,
        example = "1"
    )
    val id: Int = 0,
    @ApiModelProperty(
        "The start time of the appointment",
        required = true,
        readOnly = false,
        example = "1539555316323"
    )
    val startTime: Long,
    @ApiModelProperty(
        "The end time of the appointment",
        required = true,
        readOnly = false,
        example = "1539555326323"
    )
    val endTime: Long,
    // val veterinarian: String,
    @ApiModelProperty(
        "The pet ID scheduled for the appointment",
        required = true,
        readOnly = false,
        example = "1"
    )
    val pet: Int,
    @ApiModelProperty(
        "The client ID of the owner",
        required = true,
        readOnly = false,
        example = "1"
    )
    val client: Int,
    @ApiModelProperty(
        "A description of what the veterinarian did to the animal",
        required = true,
        readOnly = false,
        example = "Cured the animal"
    )
    val description: String,
    @ApiModelProperty(
        "The status of the appointment in Integer",
        required = true,
        readOnly = true,
        example = "1"

    )
    val status: Int
) : BaseDTO {
    fun toEntity(petService: PetService, clientService: ClientService) = toEntity(this.id, petService, clientService)

    fun toEntity(newId: Int, petService: PetService, clientService: ClientService) =
        Appointment(
            id = newId,
            timeSlot = TimeSlot(startTime, endTime),
//            veterinarian = debugvet,
            description = this.description,
            client = clientService.getClientEntityById(this.client),
            pet = petService.getPetEntityById(this.pet),
            status = AppointmentStatus.values()[status]
        )
}
