package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.ScheduleDTO
import personal.ciai.vetclinic.security.AccessControlRules.SchedulesRules.AllowedForEditSchedule
import personal.ciai.vetclinic.security.AccessControlRules.SchedulesRules.AllowedForGetSchedule
import personal.ciai.vetclinic.service.ScheduleService

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Schedule for Veterinarian in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/veterinarian/{vetId:[0-9]+}/schedules")
class ScheduleController(
    @Autowired private val schedulesService: ScheduleService
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
    @AllowedForGetSchedule
    fun getVeterinarianSchedules(
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        veterinarianId: Int

    ) = schedulesService.getVeterinarianSchedules(veterinarianId)

    @ApiOperation(
        value = "View the schedule for the given year and month",
        produces = "application/json",
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
    @GetMapping("/{year}/{month}")
    @AllowedForGetSchedule
    fun getVeterinarianScheduleByDate(
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        vetId: Int,
        @ApiParam(value = "The year of the schedule", required = true)
        @PathVariable(value = "year") year: Int,
        @ApiParam(value = "The month (1-12) of the schedule", required = true)
        @PathVariable(value = "year") month: Int

    ) = schedulesService.getVeterinarianScheduleByDate(vetId, year, month)

//    @ApiOperation(
//        value = "Get a Schedules of the Veterinarian for the Date",
//        produces = "application/json",
//        response = SchedulesDTO::class
//    )
//    @ApiResponses(
//        value = [
//            (ApiResponse(code = 200, message = "Successfully retrieved the Schedules of the Veterinarian")),
//            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
//            (ApiResponse(
//                code = 403,
//                message = "Accessing the resource you were tyring to reach is forbidden"
//            ))
//        ]
//    )
//    @GetMapping("{date}/")
//    @AllowedForGetSchedule
//    fun getVeterinarianSchedulesForDate(
//        @ApiParam(value = "The ID of the employee", required = true) @PathVariable
//        employeeId: Int,
//        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
//        veterinarianId: Int,
//        @ApiParam(value = "The date for the schedule. (dd.mm.yyy)", required = true)
//        @PathVariable(value = "date") @DateTimeFormat(iso = DATE_TIME) date: Date
//
//    ) = schedulesService.getScheduleByIdAndStartTime(veterinarianId, date)
//
//    @ApiOperation(
//        value = "Get details of an Schedule",
//        produces = "application/json",
//        response = SchedulesDTO::class
//    )
//    @ApiResponses(
//        value = [
//            (ApiResponse(code = 200, message = "Successfully retrieved the Schedule details")),
//            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
//            (ApiResponse(
//                code = 403,
//                message = "Accessing the resource you were tyring to reach is forbidden"
//            )),
//            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
//        ]
//    )
//    @GetMapping("/{scheduleId:[0-9]+}")
//    @AllowedForGetSchedule
//    fun getOneSchedule(
//        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
//        vetId: Int,
//        @ApiParam(value = "The ID of the Schedule", required = true) @PathVariable
//        scheduleId: Int
//    ) = schedulesService.getOneScheduleById(scheduleId)

    @ApiOperation(
        value = "Set a schedule for Veterinarian for a month",
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
    @PutMapping("/{year}/{month}")
    @AllowedForEditSchedule
    fun setSchedule(
        @ApiParam(value = "The ID of the Veterinarian", required = true) @PathVariable
        vetId: Int,
        @ApiParam(value = "The year of the schedule", required = true)
        @PathVariable(value = "year") year: Int,
        @ApiParam(value = "The month (1-12) of the schedule", required = true)
        @PathVariable(value = "year") month: Int,
        @ApiParam(
            value = "Details of an Schedule to be created",
            required = true
        ) @RequestBody schedule: ScheduleDTO
    ) = schedulesService.setVetSchedule(schedule.copy(vetId = vetId,year=year,month=month,bookedBlocks = emptyList()))

}
