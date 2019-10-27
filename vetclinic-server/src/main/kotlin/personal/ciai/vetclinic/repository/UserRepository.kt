package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.User

@Repository
interface UserRepository : CrudRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>

    fun existsByUsername(username: String): Boolean

    @Query("select u from User u left join fetch u.roles where u.username = :username ")
    fun findByUsernameWithRoles(@Param("username") username: String): Optional<User>
}
