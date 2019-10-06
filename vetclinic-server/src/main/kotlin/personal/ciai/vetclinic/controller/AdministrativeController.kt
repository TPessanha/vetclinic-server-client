package personal.ciai.vetclinic.controller

import com.sun.istack.NotNull
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.service.AdministrativeService
import javax.validation.Valid


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
    fun add(@Valid @RequestBody admin: AdministrativeDTO) {
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
        @NotNull @RequestBody admin: AdministrativeDTO
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
            this.adminService.delete(id);

        // TODO to Complete implementation
    }

}
