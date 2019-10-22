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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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
        @ApiParam(value = "The date for the schedule. (dd.mm.yyy)", required = true)
        @PathVariable(value = "date") @DateTimeFormat(iso = DATE) date: Date

    ) = scheduleService.geVeterinarianSchedules(veterinarianId, date)

    @ApiOperation(
        value = "Get details of an Schedule",
        produces = "application/json",
        response = ScheduleDTO::class
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the Schedule details")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @GetMapping("/{scheduleId:[0-9]+}")
    fun getOneSchedule(
        @ApiParam(value = "The ID of the employee", required = true) @PathVariable
        employeeId: Int,
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        vetId: Int,
        @ApiParam(value = "The ID of the Schedule", required = true) @PathVariable
        scheduleId: Int
    ) = scheduleService.getOneSchedule(scheduleId)

    @ApiOperation(
        value = "Add a new Schedule for Veterinarian",
        consumes = "application/json"
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully added the Schedule")),
            (ApiResponse(code = 201, message = "Successfully created the Schedule")),
            (ApiResponse(code = 401, message = "You are not authorized to create the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @PostMapping("")
    fun addSchedule(
        @ApiParam(value = "The ID of the employee", required = true) @PathVariable
        employeeId: Int,
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        vetId: Int,
        @ApiParam(
            value = "Details of an Schedule to be created",
            required = true
        ) @RequestBody schedule: ScheduleDTO
    ) = scheduleService.saveSchedule(schedule, vetId)

    @ApiOperation(
        value = "Update the Schedule of Veterinarian",
        consumes = "application/json"
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully upade the Schedule")),
            (ApiResponse(code = 401, message = "You are not authorized to create the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @PutMapping("")
    fun updateSchedule(
        @ApiParam(value = "The ID of the employee", required = true) @PathVariable
        employeeId: Int,
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        vetId: Int,
        @ApiParam(
            value = "Details of an Schedule to be created",
            required = true
        ) @RequestBody schedule: ScheduleDTO
    ) = scheduleService.updateSchedule(schedule, vetId)
}
