package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.net.URI
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.service.ClientService

/**
 * Models a Pet DTO
 *
 * @property id Int An unique identifier for the pet
 * @property species String The pet species
 * @property age Int The age of the pet
 * @property owner String The owner of the pet
 * @property appointments List<String> A list of appointments scheduled for the pet
 * @property notes String Notes about the pet
 * @property physicalDescription String A physical description of the pet
 * @property medicalRecord String The pet's medical records
 * @property photo String A photo of the pet
 * @constructor Creates a Pet DTO.
 */
@ApiModel("Pet DTO model", description = "Used to model pets")
data class PetDTO(
    @ApiModelProperty(
        "An unique identifier for the pet",
        required = false,
        readOnly = false,
        example = "0"
    )
    val id: Int = 0,
    @ApiModelProperty(
        "The species of the pet",
        required = true,
        readOnly = false,
        example = "Bulldog"
    )
    val species: String,
    @ApiModelProperty(
        "The age of the pet",
        required = true,
        readOnly = false,
        example = "4"
    )
    val age: Int,
    @ApiModelProperty(
        "The owner of the pet",
        required = true,
        readOnly = true,
        example = "1"
    )
    val owner: Int,
//    @ApiModelProperty(
//        "A list of appointments scheduled for the pet",
//        required = false,
//        readOnly = true,
//        hidden = true
//    )
//    val appointments: List<String> = emptyList(),
    @ApiModelProperty(
        "Notes about the pet",
        required = false,
        readOnly = false,
        example = "Allergy to penicillin"
    )
    val notes: String = "",
    @ApiModelProperty(
        "A physical description of the pet",
        name = "Physical Description",
        required = false,
        readOnly = false,
        example = "Weight: 10Kg, height: 42Cm"
    )
    val physicalDescription: String = "",
    @ApiModelProperty(
        "The pet's medical records",
        name = "Medical Record",
        required = false,
        readOnly = true
    )
    val medicalRecord: String = "",
    @ApiModelProperty(
        "The resource identifier for the image",
        required = false,
        readOnly = true
    )
    val photo: String? = null
) : BaseDTO {

    fun toEntity(clientService: ClientService): Pet {
        return toEntity(this.id, clientService)
    }

    fun toEntity(newId: Int, clientService: ClientService): Pet {
        return Pet(
            id = newId,
            species = this.species,
            age = this.age,
            owner = clientService.getClientEntityById(this.owner),
            appointments = arrayListOf(),
            medicalRecord = this.medicalRecord,
            physicalDescription = this.physicalDescription,
            notes = this.notes,
            photo = if (this.photo == null) null else URI.create(this.photo)
        )
    }
}
