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
@RequestMapping("/admin")
class AdministrativeController(@Autowired val adminService: AdministrativeService) {

    @ApiOperation(value = "Get details of a single  Administrative", response = AdministrativeDTO::class)
    @ApiParam(name = "id", required = true, value = "(Required) Administrative identificator (id)")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved the administrative details"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable(value = "id", required = true) id: String) {
        // TODO to Complete implementation
    }

    @ApiOperation(value = "View a list of Administratives details", response = AdministrativeDTO::class)
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved a list of all administrative"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("")
    fun getAll() {
        // TODO to Complete implementation
    }

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
    fun add(@RequestBody admin: AdministrativeDTO) {
        if (this.adminService.exist(admin.email))
            adminService.save(admin)

        // TODO to Complete implementation
    }

    @ApiOperation(value = "Edit Administrative information")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully edit the Administrative info"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @ApiParam(name = "id", required = true, value = "(Required) Admin identificator (id)")
    @PutMapping("/{id}")
    fun update(
        @PathVariable(value = "id", required = true) id: String,
        @RequestBody admin: AdministrativeDTO
    ) {
        if (this.adminService.exist(admin.email))
            adminService.save(admin)

        // TODO to Complete implementation
    }

    @ApiOperation(value = "Delete a Administrative account")
    @ApiParam(name = "id", required = true, value = "(Required) Admin identificator (id)")
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
    fun delete(@PathVariable(value = "id", required = true) id: String) {
        if (this.adminService.exist(id))
            this.adminService.delete(id)

        // TODO to Complete implementation
    }
}
