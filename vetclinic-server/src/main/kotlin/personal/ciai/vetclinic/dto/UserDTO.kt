package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.net.URI
import personal.ciai.vetclinic.model.User

/**
 * Models a User DTO.
 *
 * @property email the email of the User.
 * @property name the name of the User.
 * @property phoneNumber the phone number of this User.
 * @property username the username of the User.
 * @property password the username of the User.
 * @property address the adress of the User.
 * @constructor Creates a User DTO.
 */

@ApiModel("User DTO model", description = "To model Users")
open class UserDTO(
    @ApiModelProperty(
        "An unique identifier for the user",
        required = false,
        readOnly = false,
        example = "1"
    )
    open val id: Int = 0,
    @ApiModelProperty("The User's Name", name = "name", required = true, readOnly = true)
    open val name: String,
    @ApiModelProperty("The User's Email", name = "email", required = true)
    open val email: String,
    @ApiModelProperty("The User's Phone Number", name = "phoneNumber", required = true)
    open val phoneNumber: Int,
    @ApiModelProperty("The User's Username", name = "username", required = true)
    open val username: String,
    @ApiModelProperty("The User's Password", name = "password", required = true)
    open val password: String,
    @ApiModelProperty("The User's Address", name = "address", required = true)
    open val address: String,
    @ApiModelProperty(
        "The resource identifier for the image",
        required = false,
        readOnly = true
    )
    open val photo: String? = null
) : Transferable {
    open fun toEntity() = toEntity(this.id)

    open fun toEntity(newId: Int) =
        User(
            id = newId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address,
            photo = if (this.photo == null) null else URI.create(this.photo)
        )
}
