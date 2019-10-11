package personal.ciai.vetclinic

import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Pet

class ExampleObjects {
    companion object exampleObjects {
        val dogExample = Pet(
            -1,
            "Bulldog",
            4,
            notes = "Happy dog",
            physicalDescription = "Big Dog",
            medicalRecord = "Medical stuff",
            photo = null
        )
        val pigExample = Pet(
            -1,
            "Pig",
            1,
            notes = "Cute",
            physicalDescription = "Fluffy",
            medicalRecord = "Medical stuff",
            photo = null
        )
        val iguanaExample = Pet(
            -1,
            "Iguana",
            2,
            notes = "Looks angry",
            physicalDescription = "Green",
            medicalRecord = "Medical stuff",
            photo = null
        )
        val petList = listOf<PetDTO>(dogExample.toDTO(), pigExample.toDTO(), iguanaExample.toDTO())
        val petListPet = listOf<Pet>(dogExample, pigExample, iguanaExample)
    }
}
