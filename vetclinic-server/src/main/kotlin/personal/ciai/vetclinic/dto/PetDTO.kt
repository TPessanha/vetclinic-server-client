package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Models a Pet DTO
 *
 * @property id Int An unique identifier for the pet
 * @property species String The pet species
 * @property age Int The age of the pet
 * @property owner String The owner of the pet
 * @property appointments List<String> A list of appointments scheduled for the pet
 * @property notes String Notes about the pet
 * @property physicalDescription String A physical description of the pet
 * @property medicalRecord String The pet's medical records
 * @property picture String A picture of the pet
 * @constructor Creates a Pet DTO.
 */
@ApiModel("Pet DTO model", description = "Used to model pets")
data class PetDTO(
    @ApiModelProperty("An unique identifier for the pet", required = true, readOnly = true)
    val id: Int,
    @ApiModelProperty("The species of the pet", required = true, readOnly = true)
    val species: String,
    @ApiModelProperty("The age of the pet", required = true, readOnly = false)
    val age: Int
//    @ApiModelProperty("The owner of the pet", required = true, readOnly = true)
//    val owner: String,
//    @ApiModelProperty("A list of appointments scheduled for the pet", required = true, readOnly = true)
//    val appointments: List<String>,
//    @ApiModelProperty("Notes about the pet", required = true, readOnly = true)
//    val notes: String,
//    @ApiModelProperty(
//        "A physical description of the pet",
//        name = "Physical Description",
//        required = true,
//        readOnly = true
//    )
//    val physicalDescription: String,
//    @ApiModelProperty("The pet's medical records", name = "Medical Record", required = true, readOnly = true)
//    val medicalRecord: String
) : BaseDTO
