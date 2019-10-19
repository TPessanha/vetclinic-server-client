package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table
import personal.ciai.vetclinic.dto.BaseDTO
import personal.ciai.vetclinic.dto.UserDTO

/**
 * Models a User.
 *
 * @property email the email of the User.
 * @property name the name of the User.
 * @property phoneNumber the phone number of this User.
 * @property username the username of the User.
 * @property password the username of the User.
 * @property address the adress of the User.
 * @constructor Creates a User DTO.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
    id: Int,
    val email: String,
    val name: String,
    val phoneNumber: Int,
    val username: String,
    val password: String,
    val address: String,
    @Column(nullable = true)
    var photo: URI? = null
) : IdentifiedEntity(id) {
    override fun toDTO(): BaseDTO =
        UserDTO(
            id = this.id,
            email = this.email,
            name = this.name,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address,
            photo = photo?.toString()
        )
}
