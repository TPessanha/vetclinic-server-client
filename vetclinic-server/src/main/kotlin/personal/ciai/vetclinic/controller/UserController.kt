package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
/*
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
*/
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.CredentialsDTO
import personal.ciai.vetclinic.service.UserService

@Api(
    value = "VetClinic Management System - User API",
    description = "Management operation of Users in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("")
class UserController(@Autowired val userService: UserService) {
    @ApiOperation(
        value = "Login as a registered user",
        consumes = "application/json"
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully logged in")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @PostMapping("/login")
    fun login(
        @ApiParam(value = "The credentials of the user", required = true)
        @RequestBody credentials: CredentialsDTO
    ) {
    }
    /*

    @ApiOperation(
        value = "Register as a registered user",
        produces = "application/json",
        responseContainer = "List",
        response = AppointmentDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully registered")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @PostMapping("/signup")
    fun register

     */
}
