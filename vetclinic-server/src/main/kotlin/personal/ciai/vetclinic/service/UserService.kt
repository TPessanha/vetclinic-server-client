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

    private fun saveUser(userDTO: UserDTO, id: Int = 0) {
        val newUser = userDTO.toEntity(id)
        repository.save(newUser)
    }

    fun updatePet(userDTO: UserDTO, id: Int) {
        if (id <= 0 || !repository.existsById(userDTO.id))
            throw NotFoundException("User with id ($id) not found")

        saveUser(userDTO, id)
    }

    fun addPet(userDTO: UserDTO) {
        if (userDTO.id != 0)
            throw ExpectationFailedException("User id must be 0 in insertion")

        saveUser(userDTO)
    }
}
