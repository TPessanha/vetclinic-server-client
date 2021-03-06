package personal.ciai.vetclinic.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
import personal.ciai.vetclinic.security.AccessControlRules.PetsRules.AllowedForEditPet
import personal.ciai.vetclinic.security.AccessControlRules.PetsRules.AllowedForGetClientPets
import personal.ciai.vetclinic.security.AccessControlRules.PetsRules.AllowedForGetOnePet
import personal.ciai.vetclinic.security.AccessControlRules.UserRules.IsPrincipalAccountOwner
import personal.ciai.vetclinic.service.PetService

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
    @AllowedForGetClientPets
    fun getAllClientPets(
        @ApiParam(value = "The ID of the client", required = true, defaultValue = "1") @PathVariable
        clientId: Int
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
    @GetMapping("/{petId:[0-9]+}")
    @AllowedForGetOnePet
    fun getOnePet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int
    ) = petService.getPetById(petId)

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
    @IsPrincipalAccountOwner
    fun addPet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "Details of a pet to be created", required = true) @RequestBody
        pet: PetDTO
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
    @PutMapping("/{petId:[0-9]+}")
    @AllowedForEditPet
    fun updatePet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int,
        @ApiParam(value = "Details of a pet to be updated", required = true) @RequestBody
        pet: PetDTO
    ) = petService.updatePet(pet, id = petId)

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
    @PutMapping("/{petId:[0-9]+}/photo")
    @AllowedForEditPet
    fun savePhoto(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int,
        @RequestParam("photo")
        photo: MultipartFile
    ) = petService.updatePhoto(petId, photo)

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
    @GetMapping("/{petId:[0-9]+}/photo")
    fun getPhoto(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(petService.getPhoto(petId))

    @ApiOperation(value = "Delete a pet")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Successfully deleted a pet"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
        ]
    )
    @DeleteMapping("/{petId:[0-9]+}")
    @AllowedForEditPet
    fun deletePet(
        @ApiParam(value = "The ID of the client", required = true) @PathVariable
        clientId: Int,
        @ApiParam(value = "The ID of the pet", required = true) @PathVariable
        petId: Int
    ) = petService.deletePet(petId)
}
