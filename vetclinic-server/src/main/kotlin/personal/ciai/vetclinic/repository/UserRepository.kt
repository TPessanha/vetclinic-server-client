package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.User
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Int>
{
    fun findByUsername(username: String): Optional<User>
}
