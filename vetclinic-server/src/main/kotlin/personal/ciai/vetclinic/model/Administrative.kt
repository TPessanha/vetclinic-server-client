package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Table
import personal.ciai.vetclinic.dto.AdministrativeDTO

@Entity
@DiscriminatorValue("Admin")
@Table(name = "administratives")
class Administrative(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI,
    employeeId: Int

) : Employee(id, employeeId, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO() = AdministrativeDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        employeeId = employeeId,
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString()
    )
}
