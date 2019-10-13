package personal.ciai.vetclinic

import java.net.URI
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.dto.PetDTO
import personal.ciai.vetclinic.model.Administrative
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


        // Administrative

        val admin1 = Administrative(
            -1,
            "admin1@vetclinic.pt",
            "Simmons Gon",
            932521643,
            "Admin1",
            "1234",
            "Rua de Pina 23, 2341-323, Lisboa",
            URI.create("admin/1")
        )
        val admin2 = Administrative(
            -1,
            "admin2@vetclinic.pt",
            "Lufy D Monkey",
            925653938,
            "LufyD",
            "1234",
            "Grand Line 3, Lisboa",
            URI.create("admin/2")
        )
        val admin3 = Administrative(
            -1,
            "admin2@vetclinic.pt",
            "Homer Simpson",
            936925673,
            "Admin3",
            "1234",
            "Rua da Esqurda 4, 4323-543, Lisboa",
            URI.create("admin/3")
        )

        val listAdmin = listOf<Administrative>(admin1, admin2, admin3)
        val listAdminDTO = listOf<AdministrativeDTO>(admin1.toDTO(), admin2.toDTO(), admin3.toDTO())
    }
}
