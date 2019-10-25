package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.net.URI
import personal.ciai.vetclinic.model.Administrator
import java.nio.file.Paths

/**
 * Models a Administrator DTO.
 *
 * @property name the name of the Administrator.
 * @property username the user name of the Administrator account.
 * @property photo the photo belonging to the Administrator.
 * @property email the email of the Administrator.
 * @property password the password of the Administrator.
 * @property phoneNumber the phone number belonging to the Administrator.
 * @property address the address of the Administrator.
 * @constructor Creates a Administrator DTO.
 */
@ApiModel("Administrator DTO model", description = "Used to model Administrator")
data class AdministratorDTO(

    @ApiModelProperty(
        "An unique identifier for the Administrator",
        required = true,
        readOnly = false,
        example = "34"
    )
    val id: Int,

    @ApiModelProperty(
        "The Administrator  unique employee Id",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Dunia Das Flores"
    )
    val employeeId: Int,

    @ApiModelProperty(
        "The Administrator  full name",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Wilford A Flanders"
    )
    val name: String,

    @ApiModelProperty(
        "The Administrator user name",
        name = "username",
        required = true,
        readOnly = false,
        example = "AdminExemplo"
    )
    val username: String,

    @ApiModelProperty(
        "The Administrator email",
        name = "email",
        required = true,
        readOnly = false,
        example = "email@exemplo.pt"
    )
    val email: String,

    @ApiModelProperty("The Administrator Password", name = "password", required = true)
    val password: String,

    @ApiModelProperty(
        "The Administrator phone number",
        name = "phoneNumber",
        required = true,
        readOnly = false,
        example = "923434264"
    )
    val phoneNumber: Int,

    @ApiModelProperty(
        "The Administrator address",
        name = "address",
        required = true,
        readOnly = false,
        example = "R Nossa Senhora Fátima 117, 3400-233, Lisboa"
    )
    val address: String
) : Transferable {

    fun toEntity(picturePath: String) = toEntity(this.id, picturePath)

    fun toEntity(newId: Int, picturePath: String) =
        Administrator(
            id = newId,
            employeeId = employeeId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address,
            photo = Paths.get(picturePath, this.id.toString()).toUri()
        )

    fun toEntity(entity: Administrator, picturePath: String) =
        Administrator(
            id = entity.id,
            employeeId = employeeId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = entity.password,
            address = this.address,
            photo = Paths.get(picturePath, this.id.toString()).toUri()
        )
}
