package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.UserDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.repository.UserRepository

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

    fun saveUser(userDTO: UserDTO, id: Int = 0) {
        if (id > 0 && !repository.existsById(userDTO.id))
            throw NotFoundException("User with id ($userDTO) not found")

        val user = userDTO.toEntity(id)

        if (id < 0 || (id == 0 && userDTO.id != 0))
            throw ExpectationFailedException("Id must be 0 in insertion or > 0 for update")

        repository.save(userDTO.toEntity())
    }
}
