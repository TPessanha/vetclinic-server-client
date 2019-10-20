package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.Date
import personal.ciai.vetclinic.model.Appointment
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
        "The date of the appointment in Long",
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
    val description: String
) : BaseDTO {
    fun toEntity(petService: PetService, clientService: ClientService) = toEntity(this.id, petService, clientService)

    fun toEntity(newId: Int, petService: PetService, clientService: ClientService) =
        Appointment(
            id = newId,
            date = Date(this.date),
//            veterinarian = debugvet,
            description = this.description,
            client = clientService.getClientEntityById(this.client),
            pet = petService.getPetEntityById(this.pet)
        )
}
