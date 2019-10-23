package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.service.AdministrativeService
import personal.ciai.vetclinic.service.EmployeeService

@Api(
    value = "VetClinic Management System - Administrator API",
    description = "Management Administrative operations in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("employees/{employeeId:[0-9]+}/administratives")
class AdministrativeController(
    @Autowired val administrativeService: AdministrativeService,
    @Autowired private val employeeService: EmployeeService
) {

    @ApiOperation(
        value = "Get details of a single  Administrative",
        produces = "application/json",
        response = AdministratorDTO::class
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
    @GetMapping("/{adminId:[0-9]+}", produces = [APPLICATION_JSON_VALUE])
    fun getAdministrative(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(
            name = "adminId",
            required = true,
            value = "(Required) Administrative identificator (id)"
        ) @PathVariable(
            value = "adminId",
            required = true
        ) adminId: Int
    ) = administrativeService.getAdministrativeById(adminId)

    @ApiOperation(
        value = "View a list of Administratives details",
        produces = "application/json",
        responseContainer = "List",
        response = AdministratorDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved a list of all administrative"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("", produces = [APPLICATION_JSON_VALUE])
    fun getAllAdministrative(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int
    ) = administrativeService.getAllAdministrative()

    @ApiOperation(value = "Add a new Administrative account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully added a new administrative"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PostMapping("", consumes = [APPLICATION_JSON_VALUE])
    fun addAdministrative(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(required = true, value = "(Required) Administrative info necessary to created a new account")
        @RequestBody admin: AdministratorDTO
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
    @PutMapping("/{adminId:[0-9]+}", consumes = [APPLICATION_JSON_VALUE])
    fun updateAdministrative(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(name = "adminId", required = true, value = "(Required) Admin identificator (id)") @PathVariable(
            value = "adminId", required = true
        ) adminId: Int,
        @ApiParam(required = true, value = "(Required) Admin information to be changed")
        @RequestBody admin: AdministratorDTO
    ) = administrativeService.update(admin, adminId)

    @ApiOperation(value = "Delete a Administrative account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully delete the administrative"),
            ApiResponse(code = 404, message = "The resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @DeleteMapping("/{adminId:[0-9]+}")
    fun deleteAdministrative(
        @ApiParam(name = "employeeId", value = "(Required) The ID of the employee", required = true) @PathVariable(
            value = "employeeId",
            required = true
        )
        employeeId: Int,
        @ApiParam(name = "adminId", required = true, value = "(Required) Admin identificator (id)") @PathVariable(
            value = "adminId", required = true
        ) adminId: Int
    ) = administrativeService.delete(adminId)
}
