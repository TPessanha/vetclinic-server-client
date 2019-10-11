package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.service.AppointmentService
import personal.ciai.vetclinic.service.PetService

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Appointments in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/appointments")
class AppointmentController(
    @Autowired
    val appointmentService: AppointmentService
){
    @ApiOperation(
        value = "View a list of appointments",
        produces = "application/json",
        responseContainer = "List",
        response = AppointmentDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of appointments")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("")
    fun getAllPets() = appointmentService.findAllAppointments()

    @ApiOperation(
        value = "(Debug) Create a new appointment",
        consumes = "application/json"
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully added the appointment")),
            (ApiResponse(code = 201, message = "Successfully created the appointment")),
            (ApiResponse(code = 401, message = "You are not authorized to create the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @PostMapping("")
    fun addPet(
        @ApiParam(value = "Details of an appointment to be created", required = true) @RequestBody appointment: AppointmentDTO
    ) = appointmentService.saveAppointment(appointment.copy(id = 0))
}
