package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.net.URI
import personal.ciai.vetclinic.model.Client

@ApiModel("Client DTO model", description = "Used to transport client data")
data class ClientDTO(
    @ApiModelProperty(
        "An unique identifier for the client",
        required = true,
        readOnly = false,
        example = "1"
    )
    val id: Int = 0,
    @ApiModelProperty(
        "The name of the client",
        required = true,
        readOnly = false,
        example = "Luis"
    )
    val name: String,
    @ApiModelProperty(
        "The email of the client",
        required = true,
        readOnly = false,
        example = "l@gmail.com"
    )
    val email: String,
    @ApiModelProperty(
        "The phone number of the client",
        required = true,
        readOnly = false,
        example = "912345678"
    )
    val phoneNumber: Int,
    @ApiModelProperty(
        "The username of the client",
        required = true,
        readOnly = false,
        example = "user123"
    )
    val username: String,
    @ApiModelProperty(
        "The password of the client",
        required = true,
        readOnly = false,
        example = "abc12345"
    )
    val password: String,
    @ApiModelProperty(
        "The address of the client",
        required = true,
        readOnly = false,
        example = "rua abc"
    )
    val address: String,
    @ApiModelProperty(
        "The resource identifier for the image",
        required = false,
        readOnly = true
    )
    val photo: String? = null
) : Transferable {
    fun toEntity() = toEntity(this.id)

    fun toEntity(newId: Int) = Client(
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
