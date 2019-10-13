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
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.service.VeterinarianService

@Api(
    value = "VetClinic Management System - Veterinarian API",
    description = "Management of Veterinarian in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/veterinarian")
class VeterinarianController(@Autowired val veterinarianService: VeterinarianService) {

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
    @GetMapping("/{id}")
    fun getVeterinarian(
        @ApiParam(name = "id", required = true, value = "(Required) Veterinarian identificator (id)")
        @PathVariable(value = "id", required = true) id: String
    ) {
        // TODO to Complete implementation
    }

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
    fun getAllVeterinarian() {
        // TODO to Complete implementation
    }

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
        @ApiParam(required = true, value = "(Required) Veterinarian info necessary to created a new account")
        @RequestBody admin: VeterinarianDTO
    ) {
        // TODO to Complete implementation
    }

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
    @PutMapping("/{id}")
    fun updateVeterinarian(
        @ApiParam(name = "id", required = true, value = "(Required) Admin identificator (id)")
        @PathVariable(value = "id", required = true) id: String,
        @ApiParam(required = true, value = "(Required) Admin information to be changed")
        @RequestBody admin: VeterinarianDTO
    ) {
        // TODO to Complete implementation
    }

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
    @DeleteMapping("/{id}")
    fun deleteVeterinarian(
        @ApiParam(name = "id", required = true, value = "(Required) Admin identificator (id)")
        @PathVariable(value = "id", required = true) id: String
    ) {
        // TODO to Complete implementation
    }
}
