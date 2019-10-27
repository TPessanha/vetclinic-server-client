package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import java.security.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.NotificationDTO
import personal.ciai.vetclinic.service.ClientService
import personal.ciai.vetclinic.service.NotificationService

@Api(
    value = "VetClinic Management System - Client API",
    description = "Management operation of Notifications in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("clients/{clientId}/notifications")
class NotificationController(
    @Autowired private val clientService: ClientService,
    @Autowired private val notificationService: NotificationService

) {

    @ApiOperation(
        value = "View the list of client notification",
        produces = "application/json",
        responseContainer = "List",
        response = NotificationDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of notification")),
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
        clientId: Int,
        principal: Principal
    ) = notificationService.getAllNotification(clientId)

    @ApiOperation(
        value = "View the list of client new notification",
        produces = "application/json",
        responseContainer = "List",
        response = NotificationDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of notification")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("/")
    fun getAllNewNotification(
        @ApiParam(value = "The ID of the client", required = false, defaultValue = "1") @PathVariable
        clientId: Int,
        principal: Principal
    ) = notificationService.getAllNewNotification(clientId)

    @ApiOperation(
        value = "View the client  notification",
        produces = "application/json",
        response = NotificationDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the notification")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("/{notifId:[0-9]+}")
    fun getNotification(
        @ApiParam(value = "The ID of the client", required = false, defaultValue = "1") @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the notification", required = true, defaultValue = "1") @PathVariable
        notifId: Int,
        principal: Principal
    ) = notificationService.getNotification(notifId)

    @ApiOperation(
        value = "Mark the client notification as readed",
        produces = "application/json",
        response = NotificationDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully readed the notification")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @PutMapping("/{notifId:[0-9]+}")
    fun notificationReaded(
        @ApiParam(value = "The ID of the client", required = false, defaultValue = "1") @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the notification", required = true, defaultValue = "1") @PathVariable
        notifId: Int,
        principal: Principal
    ) = notificationService.notificationReaded(notifId)
}
