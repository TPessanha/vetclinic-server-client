package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModelProperty

/**
 * Models a Employee DTO.
 *
 * @property name the name of the Employee.
 * @property photo the photo belonging to the Employee.
 * @property email the email of the Employee.
 * @property phoneNumber the phone number belonging to the Employee.
 * @property address the address of the Employee.
 * @constructor Creates a Employee DTO.
 */
data class EmployeeDTO(

    @ApiModelProperty(
        "The Employee  full name",
        name = "fullName",
        required = true,
        readOnly = true,
        example = "Dunia Das Flores"
    )
    val name: String,

    @ApiModelProperty("The Employee photo URI", name = "photo", required = false, readOnly = false)
    val photo: String?,

    @ApiModelProperty(
        "The Employee email",
        name = "email",
        required = true,
        readOnly = false,
        example = "employee@vetclinic.pt"
    )
    val email: String,

    @ApiModelProperty(
        "The Employee phone number",
        name = "phoneNumber",
        required = true,
        readOnly = false,
        example = "912344264"
    )
    val phoneNumber: Int,

    @ApiModelProperty(
        "The Employee address",
        name = "address",
        required = true,
        readOnly = false,
        example = "R da Senhora Fátima 43, 323-621, Setubal"
    )
    val address: String

) : BaseDTO
