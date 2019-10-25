package personal.ciai.vetclinic.model

import org.hibernate.annotations.NaturalId
import java.net.URI
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
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
