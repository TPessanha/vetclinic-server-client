package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.service.ScheduleService

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Schedule for Veterinarian in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/employees/{employeeId:[0-9]+}/veterinarian/{vetId:[0-9]+}/schedules")
class ScheduleController(
    @Autowired private val scheduleService: ScheduleService
) {
    @ApiOperation(
        value = "View a list of Schedules for the Veterinarian",
        produces = "application/json",
        responseContainer = "List",
        response = ScheduleDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of Schedules of the Veterinarian")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("")
    fun getVeterinarianSchedules(
        @ApiParam(value = "The ID of the employee", required = true) @PathVariable
        employeeId: Int,
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        veterinarianId: Int

    ) = scheduleService.geVeterinarianSchedules(veterinarianId)

    @ApiOperation(
        value = "View a list of Schedules for the Veterinarian from the Date",
        produces = "application/json",
        responseContainer = "List",
        response = ScheduleDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of Schedules of the Veterinarian")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("{date}")
    fun getVeterinarianSchedulesDate(
        @ApiParam(value = "The ID of the employee", required = true) @PathVariable
        employeeId: Int,
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        veterinarianId: Int,
        @ApiParam(value = "The date for the schedule", required = true)
        @PathVariable @DateTimeFormat(iso = DATE) date: Date

    ) = scheduleService.geVeterinarianSchedules(veterinarianId, date)

//    @ApiOperation(
//        value = "Get details of an appointment",
//        produces = "application/json",
//        response = AppointmentDTO::class
//    )
//    @ApiResponses(
//        value = [
//            (ApiResponse(code = 200, message = "Successfully retrieved the appointment details")),
//            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
//            (ApiResponse(
//                code = 403,
//                message = "Accessing the resource you were tyring to reach is forbidden"
//            )),
//            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
//        ]
//    )
//    @GetMapping("/{id:[0-9]+}")
//    fun getOneAppointment(
//        @ApiParam(value = "The ID of the client", required = true) @PathVariable
//        employeeId: Int,
//        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
//        VeterinarianId: Int,
//        @ApiParam(value = "The ID of the appointment", required = true) @PathVariable
//        id: Int
//    )
//
//    @ApiResponses(
//        value = [
//            (ApiResponse(code = 200, message = "Successfully added the appointment")),
//            (ApiResponse(code = 201, message = "Successfully created the appointment")),
//            (ApiResponse(code = 401, message = "You are not authorized to create the resource")),
//            (ApiResponse(
//                code = 403,
//                message = "Accessing the resource you were tyring to reach is forbidden"
//            )),
//            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
//        ]
//    )
//    @PostMapping("")
//    fun addAppointment(
//        @ApiParam(value = "The ID of the client", required = true) @PathVariable
//        employeeId: Int,
//        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
//        VeterinarianId: Int,
//        @ApiParam(
//            value = "Details of an appointment to be created",
//            required = true
//        ) @RequestBody
//        appointment: AppointmentDTO
//    )
}
