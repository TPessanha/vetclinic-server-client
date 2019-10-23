package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.nio.file.Paths
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import personal.ciai.vetclinic.dto.validation.PasswordMatches
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.service.RoleService

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
@PasswordMatches
open class UserDTO(
    @ApiModelProperty(
        "An unique identifier for the user",
        required = false,
        readOnly = false,
        example = "1"
    )
    val id: Int = 0,
    @ApiModelProperty("The User's Name", name = "name", required = true, readOnly = true)
    @NotNull
    @NotEmpty
    val name: String,
    @ApiModelProperty("The User's Email", name = "email", required = true)
    @NotNull
    @NotEmpty
    @Email
    val email: String,
    @ApiModelProperty("The User's Phone Number", name = "phoneNumber", required = true)
    @NotNull
    @NotEmpty
    @Pattern(regexp = "(\\+351)?[0-9]{9}")
    val phoneNumber: Int,
    @ApiModelProperty("The User's Username", name = "username", required = true)
    @NotNull
    @NotEmpty
    val username: String,
    @ApiModelProperty("The User's Password", name = "password", required = true)
    @NotNull
    @NotEmpty
    @Size(min = 6)
    val password: String,
    @ApiModelProperty("The User's Password again", name = "matching password", required = true)
    val passwordRepeat: String,
    @ApiModelProperty("The User's Address", name = "address", required = true)
    @NotNull
    @NotEmpty
    val address: String,
    @ApiModelProperty(
        "The resource identifier for the image",
        required = false,
        readOnly = true
    )
    open val photo: String? = null
) : Transferable {
    open fun toEntity(roleService: RoleService, picturePath: String) = toEntity(this.id, picturePath, roleService)

    open fun toEntity(newId: Int, picturePath: String, roleService: RoleService) =
        User(
            id = newId,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = BCryptPasswordEncoder().encode(this.password),
            address = this.address,
            photo = if (this.photo == null) null else Paths.get(picturePath, this.photo).toUri(),
            roles = roleService.getClientRoles(newId).toMutableList()
        )
}
