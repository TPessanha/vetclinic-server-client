package personal.ciai.vetclinic.model

import personal.ciai.vetclinic.dto.UserDTO

abstract class User<T>(
    id: Int,
    val name: String,
    val username: String,
    val password: String
) : Entity<T>(id) {
    val profileImage: ProfileImage? = null
    val email: String = ""
    var cellphoneNumber = ""
    var address = ""
}