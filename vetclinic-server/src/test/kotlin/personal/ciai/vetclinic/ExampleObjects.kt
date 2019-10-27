package personal.ciai.vetclinic

import java.net.URI
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.model.Administrator
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
            arrayListOf(),
            2,
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
            arrayListOf(),
            2,
            arrayListOf()
        )

        val vetList = listOf<Veterinarian>(vet1, vet2)
        val vetDTOList = listOf<VeterinarianDTO>(vet1.toDTO(), vet2.toDTO())

        // Administrator

        val admin1 = Administrator(
            id = -1,
            email = "admin1@vetclinic.pt",
            name = "Simmons Gon",
            phoneNumber = 932521643,
            username = "Admin1",
            password = "1234",
            address = "Rua de Pina 23, 2341-323, Lisboa",
            photo = URI.create("admin/1"),
            employeeId = 12
        )
        val admin2 = Administrator(
            id = 2,
            email = "admin2@vetclinic.pt",
            name = "Lufy D Monkey",
            phoneNumber = 925653938,
            username = "LufyD",
            password = "1234",
            address = "Grand Line 3, Lisboa",
            photo = URI.create("admin/2"),
            employeeId = 12
        )
        val admin3 = Administrator(
            id = -1,
            email = "admin2@vetclinic.pt",
            name = "Homer Simpson",
            phoneNumber = 936925673,
            username = "Admin3",
            password = "1234",
            address = "Rua da Esqurda 4, 4323-543, Lisboa",
            photo = URI.create("admin/3"),
            employeeId = 12
        )

        val listAdmin = listOf<Administrator>(admin1, admin2, admin3)
        val listAdminDTO = listOf<AdministratorDTO>(admin1.toDTO(), admin2.toDTO(), admin3.toDTO())
    }
}
