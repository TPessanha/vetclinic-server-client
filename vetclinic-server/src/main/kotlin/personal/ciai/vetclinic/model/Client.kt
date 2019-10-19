package personal.ciai.vetclinic.model

import java.net.URI
// import javax.persistence.CascadeType
import javax.persistence.Entity
// import javax.persistence.OneToMany
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
   // @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], orphanRemoval = true)
   // var appointments: MutableList<Appointment> = arrayListOf(),
    address: String,
    photo: URI?
) : User(id, email, name, phoneNumber, username, password, address, photo) {
    override fun toDTO(): ClientDTO =
        ClientDTO(
            id = this.id, email = this.email, name = this.name, phoneNumber = this.phoneNumber,
            username = this.username, password = this.password, address = this.address, photo = photo?.toString())
}
