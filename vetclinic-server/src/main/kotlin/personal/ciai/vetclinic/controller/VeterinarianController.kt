package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.service.AppointmentService
import personal.ciai.vetclinic.service.EmployeeService
import personal.ciai.vetclinic.service.VeterinarianService

@Api(
    value = "VetClinic Management System - Veterinarian API",
    description = "Management of Veterinarian in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("employees/{employeeId:[0-9]+}/veterinarians")
class VeterinarianController(
    @Autowired private val employeeService: EmployeeService,
    @Autowired private val veterinarianService: VeterinarianService,
    @Autowired private val appointmentService: AppointmentService
) {

    @ApiOperation(
        value = "Get details of a single  Veterinarian",
        produces = "application/json",
        response = VeterinarianDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved the Veterinarian details"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),

            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("/{vetId:[0-9]+}")
    fun getVeterinarian(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(name = "vetId", required = true, value = "(Required) Veterinarian identificator (id)")
        @PathVariable(value = "vetId", required = true) vetId: Int
    ) = veterinarianService.getVeterinarianById(vetId)

    @ApiOperation(
        value = "View a list of Veterinarians details",
        produces = "application/json",
        responseContainer = "List",
        response = VeterinarianDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved a list of all Veterinarian"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("")
    fun getAllVeterinarian(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int
    ) = veterinarianService.getAllVeterinarian()

    @ApiOperation(value = "Add a new Veterinarian account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully added a new Veterinarian"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PostMapping("")
    fun addVeterinarian(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(required = true, value = "(Required) Veterinarian info necessary to created a new account")
        @RequestBody vet: VeterinarianDTO
    ) = veterinarianService.save(vet)

    @ApiOperation(value = "Edit Veterinarian information", consumes = "application/json")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully edit the Veterinarian info"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "Resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PutMapping("/{vetId:[0-9]+}")
    fun updateVeterinarian(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(name = "vetId", required = true, value = "(Required) Veterinarian identificator (id)")
        @PathVariable(value = "vetId", required = true) vetId: Int,
        @ApiParam(required = true, value = "(Required) Veterinarian information to be changed")
        @RequestBody vet: VeterinarianDTO
    ) = veterinarianService.update(vet, vetId)

    @ApiOperation(value = "Delete a Veterinarian account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully delete the Veterinarian"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @DeleteMapping("/{vetId:[0-9]+}")
    fun deleteVeterinarian(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        ) employeeId: Int,
        @ApiParam(name = "vetId", required = true, value = "(Required) Veterinarian identificator (id)")
        @PathVariable(value = "vetId", required = true) vetId: Int
    ) = veterinarianService.delete(vetId)

    @ApiOperation(
        value = "Get details of a single Veterinarian Appointmets",
        produces = "application/json",
        response = AppointmentDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved the Veterinarian appointments details"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),

            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("/{vetId:[0-9]+}/appointments/{appointmentId:[0-9]+}")
    fun getVeterinarianAppointment(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(name = "vetId", required = true, value = "(Required) Veterinarian identificator (id)")
        @PathVariable(value = "vetId", required = true) vetId: Int,
        @ApiParam(name = "appointmentId", required = true, value = "(Required) Appointment identificator (id)")
        @PathVariable(value = "appointmentId", required = true) appointmentId: Int
    ) = appointmentService.getAppointmentById(appointmentId)

    @ApiOperation(
        value = "Get a List Veterinarian Appointmets",
        produces = "application/json",
        response = AppointmentDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved the Veterinarian appointments details"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),

            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("/{vetId:[0-9]+}/appointments")
    fun getVeterinarianAppointment(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(name = "vetId", required = true, value = "(Required) Veterinarian identificator (id)")
        @PathVariable(value = "vetId", required = true) vetId: Int
    ) = veterinarianService.getVeterinarianAppointments(vetId)
}