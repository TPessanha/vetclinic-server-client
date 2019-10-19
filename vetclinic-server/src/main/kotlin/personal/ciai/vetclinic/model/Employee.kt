package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Models an Employee.
 *
 * @property id the Employee's id.
 */
@MappedSuperclass
abstract class Employee<DTO>(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    @Column(nullable = false)
    var photo: URI
) : User(email, name, phoneNumber, username, password, address), Entity<DTO>

abstract class Employee(id: Int) : IdentifiedEntity(id)
