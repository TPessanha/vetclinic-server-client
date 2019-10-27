package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Models a Notification DTO
 *
 * @property id Int An unique identifier for the Notification
 * @property message String The Notification message
 * @property timeCreate Int The age of the Notification
 * @constructor Creates a Notification DTO.
 */

@ApiModel("Notification DTO model", description = "Used to model Notification")
data class NotificationDTO(

    @ApiModelProperty(
        "An unique identifier for the Notification",
        required = true,
        readOnly = true,
        example = "0"
    )
    val id: Int = 0,

    @ApiModelProperty(
        "The Message of the Notification",
        required = true,
        readOnly = false,
        example = "Appointment Cancelled"
    )
    val message: String,

    @ApiModelProperty(
        "The Notification time ",
        required = true,
        readOnly = false,
        example = "2423424"
    )
    val timeCreate: Long,

    @ApiModelProperty(
        "The Notification Client ",
        required = true,
        readOnly = false,
        example = "4"
    )
    val clientId: Int,

    @ApiModelProperty(
        "The Notification readed starus ",
        required = false,
        readOnly = false,
        example = "True"
    )
    val isReaded: Boolean

) : Transferable
