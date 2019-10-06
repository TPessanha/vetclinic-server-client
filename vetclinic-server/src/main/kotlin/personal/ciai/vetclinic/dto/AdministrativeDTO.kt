package personal.ciai.vetclinic.dto


import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Models a Administrative DTO.
 *
 * @property name the user name of the Administrative.
 * @property picture the picture belonging to the Administrative.
 * @property email the email of the Administrative.
 * @property phoneNumber the phone number belonging to the Administrative.
 * @property address the address of the Administrative.
 * @constructor Creates a Administrative DTO.
 */
@ApiModel("Administrative DTO model", description = "Used to model Administrative")
data class AdministrativeDTO(

    @ApiModelProperty(
        "The Administrative  user name",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Wilford A Flanders"
    )
    val fullName: String,

    @ApiModelProperty("The Administrative picture", name = "picture", required = false, readOnly = false)
    val picture: String?,

    @ApiModelProperty(
        "The Administrative email",
        name = "email",
        required = true,
        readOnly = false,
        example = "email@exemplo.pt"
    )
    val email: String,

    @ApiModelProperty(
        "The Administrative phone number",
        name = "phoneNumber",
        required = true,
        readOnly = false,
        example = "923434264"
    )
    val phoneNumber: Int,

    @ApiModelProperty(
        "The Administrative address",
        name = "address",
        required = false,
        readOnly = false,
        example = "R Nossa Senhora Fátima 117, 3400-233, Lisboa"
    )
    val address: String?

)
