package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

    /*
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
    @GetMapping("")
    fun getAllAppointments(
        @ApiParam(value = "The ID of the client", required = false, defaultValue = "1") @PathVariable
        clientId: Int
    ) = clientService.checkAppointments(clientId)
    */
}
