package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table
import personal.ciai.vetclinic.dto.BaseDTO
import personal.ciai.vetclinic.dto.EmployeeDTO

@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Employee(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI?
) : User(id, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO(): BaseDTO = EmployeeDTO(
        email = this.email,
        name = this.name,
        phoneNumber = this.phoneNumber,
        address = this.address,
        photo = photo?.toString()
    )
}
