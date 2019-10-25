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
        "The start time of the appointment in the month",
        required = true,
        readOnly = false,
        example = "5"
    )
    val startTime: Long,
    @ApiModelProperty(
        "The end time of the appointment in the month",
        required = true,
        readOnly = false,
        example = "10"
    )
    val endTime: Long,
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
        example = "1"

    )
    val status: Int
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
            timeSlot = TimeSlot(startTime, endTime),
            veterinarian = veterinarianService.getVeterinarianEntity(this.veterinarian),
            description = this.description,
            client = clientService.getClientEntityById(this.client),
            pet = petService.getPetEntityById(this.pet),
            status = AppointmentStatus.values()[status]
        )
}
