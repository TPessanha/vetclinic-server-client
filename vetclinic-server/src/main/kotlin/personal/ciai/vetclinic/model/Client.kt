package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.Table
import personal.ciai.vetclinic.dto.ClientDTO

@Entity
@Table(name = "client")
class Client(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
//    var appointments: List<Appointment> = emptyList(),
//    var pets: List<Pet> = emptyList(),
    address: String,
    photo: URI?
) : User(id, email, name, phoneNumber, username, password, address, photo) {
    override fun toDTO(): ClientDTO =
        ClientDTO(
            id = this.id, email = this.email, name = this.name, phoneNumber = this.phoneNumber,
            username = this.username, password = this.password, address = this.address, photo = photo?.toString())
}
