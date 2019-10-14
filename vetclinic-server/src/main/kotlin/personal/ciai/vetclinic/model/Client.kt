package personal.ciai.vetclinic.model

import personal.ciai.vetclinic.dto.UserDTO
import javax.persistence.Entity

@Entity
class Client(email: String,
             name: String,
             phoneNumber: Int,
             username: String,
             password: String,
             var appointments: List<Appointment> = emptyList(),
             var pets: List<Pet> = emptyList(),
             address: String): User(email, name, phoneNumber, username, password, address) {
    override fun toDTO(): UserDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates
    }
}
