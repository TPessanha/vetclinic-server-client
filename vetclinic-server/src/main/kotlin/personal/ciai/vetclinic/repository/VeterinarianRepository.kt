package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Veterinarian

@Repository
interface VeterinarianRepository : JpaRepository<Veterinarian, Int> {

    fun existsByUsername(username: String): Boolean

    fun getByUsername(username: String): Veterinarian
}
