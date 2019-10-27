package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Employee(
    id: Int,
    email: String,
    name: String,
    override var phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var employeeId: Int,
    @Column(unique = true, nullable = false)
    var photo: URI
) : User(id, email, name, phoneNumber, username, password, address)
