package personal.ciai.vetclinic.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.beans.factory.annotation.Autowired
import personal.ciai.vetclinic.exception.NotFoundException
import java.util.Date
import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Pet
import personal.ciai.vetclinic.repository.PetRepository

@ApiModel("Appointment DTO model", description = "Used to model appointments")
data class AppointmentDTO(
    @ApiModelProperty(
        "An unique identifier for the appointment",
        required = true,
        readOnly = false,
        example = "1"
    )
    val id: Int,
    @ApiModelProperty(
        "The date of the appointment",
        required = true,
        readOnly = false
//        example = "1"
    )
    val date: Long,
    //val veterinarian: String,
    @ApiModelProperty(
        "The pet ID scheduled for the appointment",
        required = true,
        readOnly = false,
        example = "Pet245"
    )
    val pet: Int,
    //val client: String,
    @ApiModelProperty(
        "A description of what the veterinarian did to the animal",
        required = true,
        readOnly = false,
        example = "1"
    )
    val description: String
) : BaseDTO
