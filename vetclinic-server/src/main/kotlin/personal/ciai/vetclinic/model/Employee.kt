package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "employees")
abstract class Employee(
    id: Int,
    @Column(unique = true, nullable = false)
    var employeeId: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    @Column(nullable = false)
    var photo: URI
) : User(id, email, name, phoneNumber, username, password, address)
