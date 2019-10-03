package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personal.ciai.vetclinic.dto.PetDTO

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Pets in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/pets")
class PetController {

    @ApiOperation(value = "View a list of registered pets", responseContainer = "List", response = PetDTO::class)
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the list of pets")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            ))
        ]
    )
    @GetMapping("")
    fun getAllPets() = emptyList<PetDTO>()

    @ApiOperation(value = "Get details of a single pet", response = PetDTO::class)
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully retrieved the pet details")),
            (ApiResponse(code = 401, message = "You are not authorized to view the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @ApiParam(value = "Pet id", required = true)
    @GetMapping("/{id}")
    fun getOnePet(@ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "id") id: Int) =
        PetDTO(id, "Pantufas", "Dog")
}
