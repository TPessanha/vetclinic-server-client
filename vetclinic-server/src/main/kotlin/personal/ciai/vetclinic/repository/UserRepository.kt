package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.User

@Repository
interface UserRepository : CrudRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}
