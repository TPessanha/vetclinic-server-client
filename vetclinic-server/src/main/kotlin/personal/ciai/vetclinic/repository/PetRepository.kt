package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Pet

@Repository
interface PetRepository : CrudRepository<Pet, Int> {
    @Query("select p from Pet p left join fetch p.appointments where p.id = :id")
    fun findByIdWithAppointments(@Param("id") id: Int): Optional<Pet>
}
