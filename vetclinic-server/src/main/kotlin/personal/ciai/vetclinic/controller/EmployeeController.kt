package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.EmployeeDTO
import personal.ciai.vetclinic.service.EmployeeService

@Api(
    value = "VetClinic Management System - Employee API",
    description = "Management operation of Employee in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("employees")
class EmployeeController(@Autowired private val employeeService: EmployeeService) {

    @ApiOperation(
        value = "View a list of Employee details",
        produces = "application/json",
        responseContainer = "List",
        response = EmployeeDTO::class
    )
    @ApiResponses(value = [ApiResponse(code = 200, message = "Successfully retrieved a list of all Employee")])
    @GetMapping("", produces = [APPLICATION_JSON_VALUE])
    fun getAllEmployee() = employeeService.getAllEnabledEmployee()
}
