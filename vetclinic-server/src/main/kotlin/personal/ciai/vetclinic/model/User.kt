package personal.ciai.vetclinic.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import org.hibernate.annotations.NaturalId
import org.springframework.security.core.authority.SimpleGrantedAuthority
import personal.ciai.vetclinic.dto.Transferable
import personal.ciai.vetclinic.dto.UserDTO

/**
 * Models a User.
 *
 * @property email the email of the User.
 * @property name the name of the User.
 * @property phoneNumber the phone number of this User.
 * @property username the username of the User.
 * @property password the username of the User.
 * @property address the address of the User.
 * @constructor Creates a User DAO
 */
@Entity
@Table(name = "users")
// @DiscriminatorColumn(name = "User_Type")
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
    id: Int,
    @Column(nullable = true, unique = true)
    val email: String,
    @Column(nullable = false)
    val name: String,
    open val phoneNumber: Int,
    @NaturalId
    @Column(nullable = false, unique = true, updatable = false)
    val username: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    val address: String,

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(
            name = "user_id", referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "role_id", referencedColumnName = "id"
        )]
    )
    val roles: MutableList<Role> = arrayListOf()
) : IdentifiedEntity(id) {
    override fun toDTO(): Transferable =
        UserDTO(
            id = this.id,
            email = this.email,
            name = this.name,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
//            passwordRepeat = this.password,
            address = this.address
        )

    fun getAuthorities() =
        roles.map { SimpleGrantedAuthority(it.toAuthority()) }.toMutableList()
}
