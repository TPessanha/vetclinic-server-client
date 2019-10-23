package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.UserDTO
import personal.ciai.vetclinic.exception.PreconditionFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.repository.UserRepository
import java.util.Optional

@Service
class UserService(
    @Autowired
    val repository: UserRepository
) {
    // fun login()

    // fun logout()

    // fun accessInfo()

    // fun uploadPhoto()

    // fun changeInfo()

    // fun changePassword
    fun getUserById(id: Int) = getUserEntityById(id).toDTO()

    fun getUserEntityById(id: Int): User =
        repository.findById(id).orElseThrow { NotFoundException("User with id ($id) not found") }

    private fun saveUser(userDTO: UserDTO, id: Int = 0): Optional<User> {
        val newUser = userDTO.toEntity(id)
        return Optional.of(repository.save(newUser))
    }

    fun updateUser(userDTO: UserDTO, id: Int): Optional<User> {
        if (id <= 0 || !repository.existsById(userDTO.id))
            throw NotFoundException("User with id ($id) not found")

        return saveUser(userDTO, id)
    }

    fun addUser(userDTO: UserDTO): Optional<User> {
        if (userDTO.id != 0)
            throw PreconditionFailedException("User id must be 0 in insertion")

        return saveUser(userDTO)
    }

    fun getUserEntityByUsername(username: String): Optional<User> {
        return repository.findByUsername(username)
    }
}
