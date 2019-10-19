package personal.ciai.vetclinic.model

import javax.persistence.Entity
import personal.ciai.vetclinic.dto.ClientDTO

@Entity
class Client(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
//    var appointments: List<Appointment> = emptyList(),
//    var pets: List<Pet> = emptyList(),
    address: String
) : User<ClientDTO>(id, email, name, phoneNumber, username, password, address) {
    override fun toDTO() =
        ClientDTO(
            id = this.id,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address
        )
}
