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
    val id: Int = 0,
    @ApiModelProperty("The User's Name", name = "name", required = true, readOnly = true)
    val name: String,
    @ApiModelProperty("The User's Email", name = "email", required = true)
    val email: String,
    @ApiModelProperty("The User's Phone Number", name = "phoneNumber", required = true)
    val phoneNumber: Int,
    @ApiModelProperty("The User's Username", name = "username", required = true)
    val username: String,
    @ApiModelProperty("The User's Password", name = "password", required = true)
    val password: String,
    @ApiModelProperty("The User's Address", name = "address", required = true)
    val address: String,
    @ApiModelProperty(
        "The resource identifier for the image",
        required = false,
        readOnly = true
    )
    val photo: String? = null
) : BaseDTO {
    fun toEntity(): User {
        return User(
            id = this.id,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address,
            photo = if (this.photo == null) null else URI.create(this.photo)
        )
    }
}
