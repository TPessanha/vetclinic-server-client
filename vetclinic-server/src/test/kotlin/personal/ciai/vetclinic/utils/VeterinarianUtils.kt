package personal.ciai.vetclinic.utils

import java.net.URI
import personal.ciai.vetclinic.dto.VeterinarianDTO
import personal.ciai.vetclinic.model.Veterinarian

object VeterinarianUtils {

    // Veterinarian

    val `veterinarian 1` = Veterinarian(
        id = 1,
        email = "vet1@vetclinic.pt",
        name = "Joao Han",
        phoneNumber = 954223134,
        username = "Vet1",
        password = "1234",
        address = "Rua de Cima 44, 4243-432, Lisboa",
        photo = URI.create("vets/1"),
        enabled = true
    )

    val `veterinarian 2` = Veterinarian(
        id = 2,
        email = "vet2@vetclinic.pt",
        name = "Ash Ketchum",
        phoneNumber = 925653938,
        username = "AshK",
        password = "1234",
        address = "Rua da Linha 3, Lisboa",
        photo = URI.create("vets2/2"),
        enabled = true

    )

    val `list of veterinarians` = listOf<Veterinarian>(`veterinarian 1`, `veterinarian 2`)
    val `list of veterinarians DTO` = listOf<VeterinarianDTO>(`veterinarian 1`.toDTO(), `veterinarian 2`.toDTO())
}
