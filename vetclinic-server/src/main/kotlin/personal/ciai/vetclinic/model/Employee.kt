package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Table
import personal.ciai.vetclinic.dto.EmployeeDTO
import personal.ciai.vetclinic.dto.Transferable

@Entity
@Table(name = "employees")
abstract class Employee(
    id: Int,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    var employeeId: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI
) : User(id, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO(): Transferable = EmployeeDTO(
        email = this.email,
        name = this.name,
        phoneNumber = this.phoneNumber,
        address = this.address,
        photo = photo?.toString()
    )
}
