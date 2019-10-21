package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import personal.ciai.vetclinic.dto.ClientDTO

@Entity
@Table(name = "clients")
// @DiscriminatorValue("C")
class Client(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI? = null,
    @OneToMany(mappedBy = "client")
    var appointments: MutableList<Appointment> = arrayListOf(),
    @OneToMany(mappedBy = "owner")
    var pets: MutableList<Pet> = arrayListOf()
) : User(id, email, name, phoneNumber, username, password, address, photo) {
    override fun toDTO(): ClientDTO =
        ClientDTO(
            id = this.id, email = this.email, name = this.name, phoneNumber = this.phoneNumber,
            username = this.username, password = this.password, address = this.address, photo = photo?.toString()
        )
}
