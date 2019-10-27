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
import personal.ciai.vetclinic.security.AccessControlRules.PetsRules.AllowedForGetPetAppointments
import personal.ciai.vetclinic.security.AccessControlRules.AppointmentRules.AllowedForGetOneAppointment
import personal.ciai.vetclinic.security.AccessControlRules.AppointmentRules.AllowedForAddAppointment
import personal.ciai.vetclinic.service.AppointmentService

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Appointments in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/clients/{clientId:[0-9]+}/pets/{petId:[0-9]+}/appointments")
class AppointmentController(
    @Autowired
    val appointmentService: AppointmentService
) {
    @ApiOperation(
        value = "View a list of appointments for the pet",
        produces = "application/json",
        responseContainer = "List",
        response = AppointmentDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of appointments of the pet")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("")
    @AllowedForGetPetAppointments
    fun getPetAppointments(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int
    ) = appointmentService.getPetAppointments(petId)

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
    @GetMapping("/{id:[0-9]+}")
    @AllowedForGetOneAppointment
    fun getOneAppointment(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int,
        @ApiParam(value = "The ID of the appointment", required = true) @PathVariable
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
    @AllowedForAddAppointment
    fun addAppointment(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int,
        @ApiParam(
            value = "Details of an appointment to be created",
            required = true
        ) @RequestBody
        appointment: AppointmentDTO
    ) = appointmentService.addAppointment(appointment.copy(pet = petId, client = clientId))
}
