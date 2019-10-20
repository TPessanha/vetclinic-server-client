package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
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
@DiscriminatorColumn(name = "User_Type")
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
    id: Int,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val phoneNumber: Int,
    @Column(nullable = false)
    val username: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val address: String,
    @Column(nullable = true)
    open var photo: URI? = null
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
