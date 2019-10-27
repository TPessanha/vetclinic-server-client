package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.model.Veterinarian

@ApiModel("Basic info DTO model", description = "To model lists")
class BasicSafeInfoDTO(
    @ApiModelProperty(
        "An unique identifier for the user",
        required = true,
        readOnly = false,
        example = "1"
    )
    val id: Int = 0,
    @ApiModelProperty("The User's Name", name = "name", required = true)
    val name: String,
    @ApiModelProperty("The User's Username", name = "username", required = true, readOnly = true)
    val username: String
) {
    constructor(admin: Administrator) : this(admin.id, admin.name, admin.username)
    constructor(vet: Veterinarian) : this(vet.id, vet.name, vet.username)
    constructor(client: Client) : this(client.id, client.name, client.username)
    constructor(admin: AdministratorDTO) : this(admin.id, admin.name, admin.username)
    constructor(vet: VeterinarianDTO) : this(vet.id, vet.name, vet.username)
    constructor(client: ClientDTO) : this(client.id, client.name, client.username)
}
