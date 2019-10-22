package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.net.URI
import personal.ciai.vetclinic.model.Administrative

/**
 * Models a Administrative DTO.
 *
 * @property name the name of the Administrative.
 * @property username the user name of the Administrative account.
 * @property photo the photo belonging to the Administrative.
 * @property email the email of the Administrative.
 * @property password the password of the Administrative.
 * @property phoneNumber the phone number belonging to the Administrative.
 * @property address the address of the Administrative.
 * @constructor Creates a Administrative DTO.
 */
@ApiModel("Administrative DTO model", description = "Used to model Administrative")
data class AdministrativeDTO(

    @ApiModelProperty(
        "An unique identifier for the Administrative",
        required = true,
        readOnly = false,
        example = "34"
    )
    val id: Int,

    @ApiModelProperty(
        "The Administrative  unique employee Id",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Dunia Das Flores"
    )
    val employeeId: Int,

    @ApiModelProperty(
        "The Administrative  full name",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Wilford A Flanders"
    )
    val name: String,

    @ApiModelProperty("The Administrative photo URI", name = "photo", required = false, readOnly = false)
    val photo: String?,

    @ApiModelProperty(
        "The Administrative user name",
        name = "username",
        required = true,
        readOnly = false,
        example = "AdminExemplo"
    )
    val username: String,

    @ApiModelProperty(
        "The Administrative email",
        name = "email",
        required = true,
        readOnly = false,
        example = "email@exemplo.pt"
    )
    val email: String,

    @ApiModelProperty("The Administrative Password", name = "password", required = true)
    val password: String,

    @ApiModelProperty(
        "The Administrative phone number",
        name = "phoneNumber",
        required = true,
        readOnly = false,
        example = "923434264"
    )
    val phoneNumber: Int,

    @ApiModelProperty(
        "The Administrative address",
        name = "address",
        required = true,
        readOnly = false,
        example = "R Nossa Senhora Fátima 117, 3400-233, Lisboa"
    )
    val address: String
) : Transferable {

    fun toEntity() = toEntity(this.id)

    fun toEntity(newId: Int) =
        Administrative(
            id = newId,
            employeeId = employeeId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address,
            photo = if (this.photo.isNullOrEmpty()) URI.create("default") else URI.create(this.photo)
        )

    fun toEntity(entity: Administrative) =
        Administrative(
            id = entity.id,
            employeeId = employeeId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = entity.password,
            address = this.address,
            photo = if (this.photo.isNullOrEmpty()) URI.create("default") else URI.create(this.photo)
        )
}
