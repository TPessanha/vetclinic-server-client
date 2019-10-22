package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Administrative

@Repository
interface AdministrativeRepository : JpaRepository<Administrative, Int> {

    fun getAdministrativeByEmployeeId(employeeId: Int): Optional<Administrative>
    fun existsAdministrativeByEmail(email: String): Boolean
}
