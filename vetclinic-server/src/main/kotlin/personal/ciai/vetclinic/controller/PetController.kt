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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.service.PetService

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Pets in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/pets")
class PetController(
    @Autowired
    val petService: PetService
) {

    @ApiOperation(
        value = "View a list of registered pets",
        produces = "application/json",
        responseContainer = "List",
        response = PetDTO::class
    )
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
    fun getAllPets() = petService.findAllPets()

    @ApiOperation(
        value = "Get details of a pet",
        produces = "application/json",
        response = PetDTO::class
    )
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
    @GetMapping("/{id}")
    fun getOnePet(
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "id")
        id: Int
    ) = petService.getPetById(id)

    @ApiOperation(
        value = "(Debug) Create a new pet",
        consumes = "application/json"
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully added the pet")),
            (ApiResponse(code = 201, message = "Successfully created the pet")),
            (ApiResponse(code = 401, message = "You are not authorized to create the resource")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @PostMapping("")
    fun addPet(
        @ApiParam(value = "Details of a pet to be created", required = true) @RequestBody pet: PetDTO
    ) = petService.savePet(pet.copy(id = 0))

    @ApiOperation(
        value = "Edit pet information",
        consumes = "application/json"
    )
    @ApiResponses(
        value = [
            (ApiResponse(code = 200, message = "Successfully updated the pet")),
            (ApiResponse(code = 201, message = "Successfully created the pet")),
            (ApiResponse(code = 401, message = "You are not authorized to edit the pet details")),
            (ApiResponse(
                code = 403,
                message = "Accessing the resource you were tyring to reach is forbidden"
            )),
            (ApiResponse(code = 404, message = "The resource you were trying to reach was not found"))
        ]
    )
    @PutMapping("/{id}")
    fun updatePet(
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "id")
        id: Int,
        @ApiParam(value = "Details of a pet to be updated", required = true) @RequestBody pet: PetDTO
    ) = petService.savePet(pet.copy(id = id), update = true)

    @ApiOperation(value = "Update photo of a pet")
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
    @PutMapping("/{id}/photo")
    fun savePhoto(
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "id")
        id: Int,
        @RequestParam("file")
        photo: MultipartFile
    ) = ResponseEntity.ok(petService.updatePhoto(id, photo).toString())

    @ApiOperation(
        value = "Get photo of a pet",
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
    @GetMapping("/{id}/photo")
    fun getPhoto(
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable(value = "id")
        id: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(petService.getPhoto(id))
}
