package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.nio.file.Paths
import personal.ciai.vetclinic.model.Veterinarian

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
        "The Veterinarian  unique Id",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Dunia Das Flores"
    )
    val employeeId: Int,

    @ApiModelProperty(
        "The Veterinarian  user name",
        name = "name",
        required = true,
        readOnly = true,
        example = "Nunes D Landers"
    )
    val name: String,

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
        "Tell the Veterinarian status",
        name = "address",
        required = true,
        readOnly = false
    )
    val enabled: Boolean

) : Transferable {

    fun toEntity(picturePath: String) = toEntity(this.id, picturePath)

    fun toEntity(newId: Int, picturePath: String) =
        Veterinarian(
            id = newId,
            employeeId = employeeId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address,
            photo = Paths.get(picturePath, this.id.toString()).toUri(),
            enabled = enabled,
            appointments = arrayListOf(),
            schedules = arrayListOf()
        )

    fun toEntity(entity: Veterinarian, picturePath: String) =
        Veterinarian(
            id = entity.id,
            employeeId = employeeId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = entity.password,
            address = this.address,
            photo = Paths.get(picturePath, this.id.toString()).toUri(),
            enabled = entity.enabled,
//            appointments = entity.appointments,
//            schedules = entity.schedules,
            appointments = arrayListOf(),
            schedules = arrayListOf()
        )
}
