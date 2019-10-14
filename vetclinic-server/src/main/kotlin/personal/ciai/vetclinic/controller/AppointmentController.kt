package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.service.AppointmentService

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Appointments in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/appointments")
class AppointmentController(
    @Autowired
    val appointmentService: AppointmentService
) {
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
    fun getAllAppointments(
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "petId")
        id: Int
    ) = appointmentService.getAllAppointments()

    @ApiOperation(
        value = "Get details of an appointment",
        produces = "application/json",
        response = AppointmentDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the appointment details")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @GetMapping("/{id}")
    fun getOneAppointment(
        @ApiParam(value = "The ID of the appointment", required = true) @PathVariable(value = "id")
        id: Int
    ) = appointmentService.getAppointmentById(id)

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
    fun addAppointment(
        @ApiParam(
            value = "Details of an appointment to be created",
            required = true
        )
        @RequestBody appointment: AppointmentDTO,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "petId")
        petID: Int
//        TODO add vet and client
//        @ApiParam(value = "The username of the client", required = true) @PathVariable(value = "clientId")
//        clientId: Int,
//        @ApiParam(value = "The ID of the vet", required = true) @PathVariable(value = "vetId")
//        vetId: Int
    ) = appointmentService.saveAppointment(appointment.copy(id = -1))
}
