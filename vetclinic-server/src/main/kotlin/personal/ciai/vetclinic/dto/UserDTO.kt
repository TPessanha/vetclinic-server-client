package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

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
data class UserDTO(
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
    val address: String
) : BaseDTO
