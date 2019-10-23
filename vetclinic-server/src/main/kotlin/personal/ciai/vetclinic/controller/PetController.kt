package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
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
import personal.ciai.vetclinic.security.AccessControlRules
import personal.ciai.vetclinic.service.PetService
import java.nio.file.attribute.GroupPrincipal
import java.security.Principal

@Api(
    value = "VetClinic Management System - Pet API",
    description = "Management of Pets in the CIAI 2019 Pet Clinic"
)

@RestController
@RequestMapping("/clients/{clientId:[0-9]+}/pets")
class PetController(
    @Autowired
    val petService: PetService
) {

    @ApiOperation(
        value = "View a list of the client pets",
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
    fun getAllClientPets(
        @ApiParam(value = "The ID of the client", required = false, defaultValue = "1") @PathVariable
        clientId: Int,
        principal: Principal
    ) = petService.getClientPets(clientId)

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
    @GetMapping("/{id:[0-9]+}")
    @AccessControlRules.PetsRules.AllowedForGetPet
    fun getOnePet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        id: Int,
        principal: Principal
    ) = petService.getPetById(id)

    @ApiOperation(
        value = "Create a new pet",
        consumes = "application/json",
        response = PetDTO::class
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
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "Details of a pet to be created", required = true) @RequestBody
        pet: PetDTO,
        principal: Principal
    ) = petService.addPet(pet.copy(owner = clientId))

    @ApiOperation(
        value = "Edit pet information",
        consumes = "application/json",
        produces = "application/json",
        response = PetDTO::class
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
    @PutMapping("/{id:[0-9]+}")
    @AccessControlRules.PetsRules.AllowedForEditPet
    fun updatePet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        id: Int,
        @ApiParam(value = "Details of a pet to be updated", required = true) @RequestBody
        pet: PetDTO,
        principal: Principal
    ) = petService.updatePet(pet, id = id)

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
    @PutMapping("/{id:[0-9]+}/photo")
    @AccessControlRules.PetsRules.AllowedForEditPet
    fun savePhoto(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        id: Int,
        @RequestParam("photo")
        photo: MultipartFile,
        principal: Principal
    ) = petService.updatePhoto(id, photo)

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
    @GetMapping("/{id:[0-9]+}/photo")
    @AccessControlRules.PetsRules.AllowedForEditPet
    fun getPhoto(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        id: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(petService.getPhoto(id))

    @ApiOperation(value = "Delete a pet")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully deleted a pet"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
        ]
    )
    @DeleteMapping("/{id:[0-9]+}")
    @AccessControlRules.PetsRules.AllowedForEditPet
    fun deletePet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        id: Int,
        principal: Principal
    ) = petService.deletePet(id)
}
