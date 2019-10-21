package personal.ciai.vetclinic

import java.net.URI
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.model.Administrative
import personal.ciai.vetclinic.model.Veterinarian

class ExampleObjects {
    companion object exampleObjects {

        // Veterinarian

        val vet1 = Veterinarian(
            1,
            "vet1@vetclinic.pt",
            "Joao Han",
            954223134,
            "Vet1",
            "1234",
            "Rua de Cima 44, 4243-432, Lisboa",
            URI.create("vets/1"),
            true,
            arrayListOf()
        )
        val vet2 = Veterinarian(
            -1,
            "vet2@vetclinic.pt",
            "Ash Ketchum",
            925653938,
            "AshK",
            "1234",
            "Rua da Linha 3, Lisboa",
            URI.create("vets2/2"),
            true,
            arrayListOf()
        )

        val vetList = listOf<Veterinarian>(vet1, vet2)
        val vetDTOList = listOf<VeterinarianDTO>(vet1.toDTO(), vet2.toDTO())

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
