package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Pet

@Repository
interface PetRepository : CrudRepository<Pet, Int> {
    @Query("select p from Pet p left join fetch p.appointments left join fetch p.owner where p.id = :id")
    fun findByIdWithAppointments(@Param("id") id: Int): Optional<Pet>

    @Query("SELECT p from Pet p WHERE p.enabled = ?1")
    fun findAllByEnabled(enabled: Boolean): List<Pet>
}
