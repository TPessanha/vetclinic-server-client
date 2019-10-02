package personal.ciai.vetclinic.model

import io.swagger.annotations.ApiModel
import personal.ciai.vetclinic.dto.PetDTO
import kotlin.reflect.full.memberProperties

@ApiModel("Pet model", description = "Used to model pets")
class Pet(val id: Int, val name: String, val species: String) {

    fun toDTO() = with(::PetDTO) {
        val propertiesByName = Pet::class.memberProperties.associateBy { it.name }
        callBy(parameters.associate { parameter ->
            parameter to propertiesByName[parameter.name]?.get(this@Pet)
        })
    }
}
