package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.AppointmentStatus
import personal.ciai.vetclinic.model.TimeSlot
import personal.ciai.vetclinic.service.ClientService
import personal.ciai.vetclinic.service.PetService
import personal.ciai.vetclinic.service.VeterinarianService

@ApiModel("Appointment DTO model", description = "Used to model appointments")
data class AppointmentDTO(
    @ApiModelProperty(
        "An unique identifier for the appointment",
        required = true,
        readOnly = false,
        example = "0"
    )
    val id: Int = 0,

    @ApiModelProperty(
        "The year of the appointment",
        required = true,
        readOnly = false,
        example = "2019"
    )
    val year: Int,

    @ApiModelProperty(
        "The month of the appointment",
        required = true,
        readOnly = false,
        example = "10"
    )
    val month: Int,

    @ApiModelProperty(
        "The start time of the appointment in the month",
        required = true,
        readOnly = false,
        example = "5"
    )
    val startTime: Int,

    @ApiModelProperty(
        "The end time of the appointment in the month",
        required = true,
        readOnly = false,
        example = "10"
    )
    val endTime: Int,

    @ApiModelProperty(
        "The veterinarian ID for the appointment",
        required = true,
        readOnly = false,
        example = "1"
    )
    val veterinarian: Int,
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
        example = "0"

    )
    val status: Int,

    @ApiModelProperty(
        "A justification for refused appointments",
        required = false,
        readOnly = true,
        example = "Had other appointment at the same time"
    )
    val justification: String = ""
) : Transferable {
    fun toEntity(petService: PetService, clientService: ClientService, veterinarianService: VeterinarianService) =
        toEntity(this.id, petService, clientService, veterinarianService)

    fun toEntity(
        newId: Int,
        petService: PetService,
        clientService: ClientService,
        veterinarianService: VeterinarianService
    ) =
        Appointment(
            id = newId,
            month = month,
            year = year,
            timeSlot = TimeSlot(startTime, endTime),
            veterinarian = veterinarianService.getVeterinarianEntity(this.veterinarian),
            description = this.description,
            client = clientService.getClientEntityById(this.client),
            pet = petService.getPetEntityById(this.pet),
            status = AppointmentStatus.values()[status],
            justification = justification
        )
}
