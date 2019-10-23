package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Administrator

@Repository
interface AdministratorRepository : JpaRepository<Administrator, Int> {

    fun getAdministratorByEmployeeId(employeeId: Int): Optional<Administrator>
    fun existsAdministratorByEmail(email: String): Boolean
}
