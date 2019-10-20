package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.Table
import personal.ciai.vetclinic.dto.AdministrativeDTO

@Entity
@Table(name = "administrative")
class Administrative(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI,
    enabled: Boolean
) : Employee(id, email, name, phoneNumber, username, password, address, photo, enabled) {

    override fun toDTO() = AdministrativeDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString(),
        enabled = enabled
    )
}
