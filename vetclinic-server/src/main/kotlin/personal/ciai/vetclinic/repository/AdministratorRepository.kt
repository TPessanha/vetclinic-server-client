package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Administrator

@Repository
interface AdministratorRepository : JpaRepository<Administrator, Int> {

    fun existsByUsername(username: String): Boolean
}
