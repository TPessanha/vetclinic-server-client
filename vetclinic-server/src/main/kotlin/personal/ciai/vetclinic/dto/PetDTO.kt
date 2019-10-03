package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Models a Pet DTO.
 *
 * @property id the id of the Pet.
 * @property name the name of the Pet.
 * @property species the species of this Pet.
 * @constructor Creates a Pet DTO.
 */
@ApiModel("Pet DTO model", description = "Used to model pets")
data class PetDTO(
    @ApiModelProperty("The Pet ID", name = "id", required = true, readOnly = true)
    override val id: Int,
    @ApiModelProperty("The Pet name", name = "name", required = true, readOnly = true)
    val name: String,
    @ApiModelProperty("The Pet species", name = "species", required = true, readOnly = true)
    val species: String
) : BaseDTO
