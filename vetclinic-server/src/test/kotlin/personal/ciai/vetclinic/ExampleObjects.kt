package personal.ciai.vetclinic

import personal.ciai.vetclinic.model.Pet

class ExampleObjects {
    companion object exampleObjects {
        val dog = Pet(
            1,
            "Bulldog",
            5,
            notes = "Pretty dog",
            physicalDescription = "Large dog",
            medicalRecord = "medical record",
            photo = null
        )
    }
}
