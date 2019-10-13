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
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.service.AdministrativeService

@Api(
    value = "VetClinic Management System - Administrative API",
    description = "Management operation of Administrative in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/administrative")
class AdministrativeController(@Autowired val administrativeService: AdministrativeService) {

    @ApiOperation(
        value = "Get details of a single  Administrative",
        produces = "application/json",
        response = AdministrativeDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved the administrative details"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),

            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("/{id}")
    fun getAdministrative(
        @ApiParam(name = "id", required = true, value = "(Required) Administrative identificator (id)")
        @PathVariable(value = "id", required = true) id: Int
    ) = administrativeService.getAdministrativeById(id)

    @ApiOperation(
        value = "View a list of Administratives details",
        produces = "application/json",
        responseContainer = "List",
        response = AdministrativeDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved a list of all administrative"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("")
    fun getAllAdministrative() = administrativeService.getAllAdministrative()

    @ApiOperation(value = "Add a new Administrative account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully added a new administrative"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PostMapping("")
    fun addAdministrative(
        @ApiParam(required = true, value = "(Required) Administrative info necessary to created a new account")
        @RequestBody admin: AdministrativeDTO
    ) = administrativeService.save(admin)

    @ApiOperation(value = "Edit Administrative information", consumes = "application/json")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully edit the Administrative info"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "Resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PutMapping("/{id}")
    fun updateAdministrative(
        @ApiParam(name = "id", required = true, value = "(Required) Admin identificator (id)")
        @PathVariable(value = "id", required = true) id: Int,
        @ApiParam(required = true, value = "(Required) Admin information to be changed")
        @RequestBody admin: AdministrativeDTO
    ) = administrativeService.update(admin, id)

    @ApiOperation(value = "Delete a Administrative account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully delete the administrative"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @DeleteMapping("/{id}")
    fun deleteAdministrative(
        @ApiParam(name = "id", required = true, value = "(Required) Admin identificator (id)")
        @PathVariable(value = "id", required = true) id: Int
    ) = administrativeService.delete(id)
}
