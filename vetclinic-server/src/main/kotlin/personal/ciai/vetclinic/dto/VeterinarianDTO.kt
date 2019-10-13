package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Models a Veterinarian DTO.
 *
 * @property name the name of the Veterinarian.
 * @property username the username of the Veterinarian.
 * @property password the password of the Veterinarian.
 * @property email the email of the Veterinarian.
 * @property phoneNumber the phone number belonging to the Veterinarian.
 * @property address the address of the Veterinarian.
 * @property photo the photo belonging to the Veterinarian.
 * @constructor Creates a Veterinarian DTO.
 */
@ApiModel("Veterinarian DTO model", description = "Used to model Veterinarian")
data class VeterinarianDTO(

    @ApiModelProperty(
        "An unique identifier for the Veterinarian",
        required = true,
        readOnly = false,
        example = "45"
    )
    val id: Int,

    @ApiModelProperty(
        "The Veterinarian  user name",
        name = "name",
        required = true,
        readOnly = true,
        example = "Nunes D Landers"
    )
    val name: String,

    @ApiModelProperty("The Veterinarian photo URI", name = "photo", required = false, readOnly = false)
    val photo: String,

    @ApiModelProperty(
        "The Veterinarian user name",
        name = "username",
        required = true,
        readOnly = false,
        example = "dnunes"
    )
    val username: String,

    @ApiModelProperty(
        "The Veterinarian email",
        name = "email",
        required = true,
        readOnly = false,
        example = "vet@vetclinic.pt"
    )
    val email: String,

    @ApiModelProperty("The Veterinarian Password", name = "password", required = true)
    val password: String,

    @ApiModelProperty(
        "The Veterinarian phone number",
        name = "phoneNumber",
        required = true,
        readOnly = false,
        example = "952321353"
    )

    val phoneNumber: Int,

    @ApiModelProperty(
        "The Veterinarian address",
        name = "address",
        required = true,
        readOnly = false,
        example = "A Maria Penhora 7, 4300-533, Setubal"
    )
    val address: String,

    @ApiModelProperty(
        "A list of Veterinarian scheduled appointments",
        required = false,
        readOnly = true
    )
    val appointments: List<Int>?

) : BaseDTO
