package personal.ciai.vetclinic.model

import javax.persistence.Column
import javax.persistence.MappedSuperclass

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

@MappedSuperclass
abstract class User(
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
    val address: String
)
