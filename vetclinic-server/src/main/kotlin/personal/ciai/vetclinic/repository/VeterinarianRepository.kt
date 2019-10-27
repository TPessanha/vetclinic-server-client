package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Veterinarian

@Repository
interface VeterinarianRepository : CrudRepository<Veterinarian, Int> {

    fun existsByUsername(username: String): Boolean

    @Query("SELECT v from Veterinarian v WHERE v.enabled = ?1")
    fun findAllByEnabled(enabled: Boolean): List<Veterinarian>

    fun getByUsername(username: String): Veterinarian
}
