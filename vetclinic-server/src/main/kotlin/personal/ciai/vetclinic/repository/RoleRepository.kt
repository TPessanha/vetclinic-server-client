package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Role

@Repository
interface RoleRepository : CrudRepository<Role, Int> {
    @Query("select u.roles from Role r inner join r.users u where u.id = :id")
    fun findByUser(@Param("id") id: Int): List<Role>
}
