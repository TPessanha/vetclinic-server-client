package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import personal.ciai.vetclinic.dto.VeterinarianDTO

@Entity
@Table(name = "veterinarians")
class Veterinarian(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI,
    enabled: Boolean,
    @OneToMany(mappedBy = "veterinarian", cascade = [CascadeType.ALL], orphanRemoval = true)
    var appointments: MutableList<Appointment> = emptyList<Appointment>().toMutableList()
) : Employee(id, email, name, phoneNumber, username, password, address, photo, enabled) {

    override fun toDTO() = VeterinarianDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString(),
        appointments = appointments.map { it.toDTO() },
        enabled = enabled
    )
}
