package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.dto.AppointmentDTO
import personal.ciai.vetclinic.dto.ClientDTO
import personal.ciai.vetclinic.security.AccessControlRules
import personal.ciai.vetclinic.service.ClientService

@Api(
    value = "VetClinic Management System - Client API",
    description = "Management operation of Clients in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/clients")
class ClientController(
    @Autowired val clientService: ClientService
) {

    @ApiOperation(
        value = "View the list of client appointments",
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
    @GetMapping("{clientId}/appointments")
    @AccessControlRules.ClientRules.AllowedForGetClientAppointments
    fun getAllClientAppointments(
        @ApiParam(value = "The ID of the client", required = true, defaultValue = "1") @PathVariable
        clientId: Int
    ) = clientService.checkAppointments(clientId)

    @ApiOperation(
        value = "View the list of client appointments",
        produces = "application/json",
        response = ClientDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the client details")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("{clientId:[0-9]+}")
    @AccessControlRules.ClientRules.AllowedForGetClientDetails
    fun getClientInfo(
        @ApiParam(value = "The ID of the client", required = true, defaultValue = "1") @PathVariable
        clientId: Int
    ) = clientService.getClientById(clientId)

    @ApiOperation(
        value = "Edit client information",
        consumes = "application/json",
        produces = "application/json",
        response = ClientDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully updated the client")),
            (ApiResponse(code = 201, message = "Successfully created the client")),
            (ApiResponse(code = 401, message = "You are not authorized to edit the client details")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @PutMapping("/{clientId:[0-9]+}")
    @AccessControlRules.ClientRules.AllowedForEditClientDetails
    fun updateClient(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "Details of a client to be updated", required = true) @RequestBody
        client: ClientDTO
    ) = clientService.updateClient(client,clientId)

    @ApiOperation(value = "Update photo of a client")
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully updated the photo")),
            (ApiResponse(code = 201, message = "Successfully created the photo")),
            (ApiResponse(code = 401, message = "You are not authorized to create the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found")),
            (ApiResponse(code = 415, message = "Photos can only be of type (jpg/png)"))
        ]
    )
    @PutMapping("/{clientId:[0-9]+}/photo")
    @AccessControlRules.ClientRules.AllowedForEditClientDetails
    fun savePhoto(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @RequestParam("photo")
        photo: MultipartFile
    ) = clientService.updatePhoto(clientId, photo)

    @ApiOperation(
        value = "Get photo of a client",
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
    @GetMapping("/{clientId:[0-9]+}/photo")
    fun getPhoto(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(clientService.getPhoto(clientId))
}
