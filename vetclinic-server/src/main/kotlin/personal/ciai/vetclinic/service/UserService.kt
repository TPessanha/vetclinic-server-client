package personal.ciai.vetclinic.service

import java.util.Optional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.UserDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.repository.UserRepository

@Service
class UserService(
    @Autowired
    var users: UserRepository
) {

    fun findUser(username: String): Optional<User> = users.findByUsername(username)

    fun addUser(user: User): Optional<User> {
        val aUser = users.findByUsername(user.username)

        return if (aUser.isPresent)
            Optional.empty()
        else {
            user.password = BCryptPasswordEncoder().encode(user.password)
            Optional.of(users.save(user))
        }
    }
    fun getUserById(id: Int) = getUserEntityById(id).toDTO()

    fun getUserEntityById(id: Int): User =
        users.findById(id).orElseThrow { NotFoundException("User with id ($id) not found") }

    private fun saveUser(userDTO: UserDTO, id: Int = 0) {
        val newUser = userDTO.toEntity(id)
        users.save(newUser)
    }

    fun updatePet(userDTO: UserDTO, id: Int) {
        if (id <= 0 || !users.existsById(userDTO.id))
            throw NotFoundException("User with id ($id) not found")

        saveUser(userDTO, id)
    }

    fun addPet(userDTO: UserDTO) {
        if (userDTO.id != 0)
            throw ExpectationFailedException("User id must be 0 in insertion")

        saveUser(userDTO)
    }
}
