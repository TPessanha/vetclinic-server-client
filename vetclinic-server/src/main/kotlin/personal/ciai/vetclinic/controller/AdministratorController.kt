package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.dto.BasicSafeInfoDTO
import personal.ciai.vetclinic.security.AccessControlRules.AdministratorsRules.AllowedForAddAdministrator
import personal.ciai.vetclinic.security.AccessControlRules.AdministratorsRules.AllowedForDeleteAdministrators
import personal.ciai.vetclinic.security.AccessControlRules.AdministratorsRules.AllowedForEditAdministrator
import personal.ciai.vetclinic.security.AccessControlRules.AdministratorsRules.AllowedForGetAdministrador
import personal.ciai.vetclinic.service.AdministratorService

@Api(
    value = "VetClinic Management System - Administrator API",
    description = "Management Administrator operations in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/administrators")
class AdministratorController(
    @Autowired val administratorService: AdministratorService
) {

    @ApiOperation(
        value = "Get details of a single  Administrator",
        produces = "application/json",
        response = AdministratorDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved the administrator details"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "The resource not found"),

            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping("/{adminId:[0-9]+}", produces = [APPLICATION_JSON_VALUE])
    @AllowedForGetAdministrador
    fun getAdministrator(
        @ApiParam(
            name = "adminId",
            required = true,
            value = "(Required) Administrator identification (id)"
        ) @PathVariable(
            value = "adminId",
            required = true
        ) adminId: Int
    ) = administratorService.getAdministratorById(adminId)

    @ApiOperation(
        value = "View a list of Administrators details",
        produces = "application/json",
        responseContainer = "List",
        response = BasicSafeInfoDTO::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully retrieved a list of all administrator"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @GetMapping(
        "",
        produces = [APPLICATION_JSON_VALUE]
    )
    fun getAllAdministrator() = administratorService.getAllAdministrator().map { BasicSafeInfoDTO(it) }

    @ApiOperation(value = "Add a new Administrator account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully added a new administrator"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PostMapping("", consumes = [APPLICATION_JSON_VALUE])
    @AllowedForAddAdministrator
    fun addAdministrator(
        @ApiParam(required = true, value = "(Required) Administrator info necessary to created a new account")
        @RequestBody admin: AdministratorDTO
    ) = administratorService.save(admin)

    @ApiOperation(value = "Edit Administrator information", consumes = "application/json")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully edit the Administrator info"),
            ApiResponse(code = 401, message = "You are not authorized to access the resource"),
            ApiResponse(code = 404, message = "Resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @PutMapping("/{adminId:[0-9]+}", consumes = [APPLICATION_JSON_VALUE])
    @AllowedForEditAdministrator
    fun updateAdministrator(
        @ApiParam(name = "adminId", required = true, value = "(Required) Admin identification (id)") @PathVariable(
            value = "adminId", required = true
        ) adminId: Int,
        @ApiParam(required = true, value = "(Required) Admin information to be changed")
        @RequestBody admin: AdministratorDTO
    ) = administratorService.update(admin.copy(id = adminId))

    @ApiOperation(value = "Delete a Administrator account")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully delete the administrator"),
            ApiResponse(code = 404, message = "The resource not found"),
            ApiResponse(
                code = 403, message = "Accessing the resource you were tyring to reach is forbidden"
            )]
    )
    @DeleteMapping("/{adminId:[0-9]+}")
    @AllowedForDeleteAdministrators
    fun deleteAdministrator(
        @ApiParam(name = "adminId", required = true, value = "(Required) Admin identification (id)") @PathVariable(
            value = "adminId", required = true
        ) adminId: Int
    ) = administratorService.delete(adminId)

    @PutMapping("/{adminId:[0-9]+}/photo")
    @ApiOperation(
        value = "Upload a photo of the Administrator",
        response = ByteArray::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully uploaded the photo")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @AllowedForEditAdministrator
    fun savePhoto(
        @ApiParam(
            name = "adminId",
            required = true,
            value = "(Required) Administrator identification (id)"
        ) @PathVariable adminId: Int,
        @RequestParam("photo")
        photo: MultipartFile
    ) = administratorService.updatePhoto(adminId, photo)

    @ApiOperation(
        value = "Get photo of the Administrator",
        response = ByteArray::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the photo")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @GetMapping("/{adminId:[0-9]+}/photo")
    fun getPhoto(
        @ApiParam(
            name = "adminId",
            required = true,
            value = "(Required) Administrator identification (id)"
        ) @PathVariable adminId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(administratorService.getPhoto(adminId))
}
