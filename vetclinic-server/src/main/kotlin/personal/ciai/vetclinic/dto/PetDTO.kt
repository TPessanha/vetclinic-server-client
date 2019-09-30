package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Pet DTO model", description = "Used to model pets")
data class PetDTO(
    @ApiModelProperty("The Pet ID", name = "id", required = true, readOnly = true)
    val id: Int,
    @ApiModelProperty("The Pet name", name = "name", required = true, readOnly = true)
    val name: String,
    @ApiModelProperty("The Pet species", name = "species", required = true, readOnly = true)
    val species: String
)
