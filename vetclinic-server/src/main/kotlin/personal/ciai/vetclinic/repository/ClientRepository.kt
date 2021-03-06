package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Client

@Repository
interface ClientRepository : CrudRepository<Client, Int> {
    @Query("select c from Client c left join fetch c.pets p where c.id = :owner AND p.enabled = true")
    fun findByIdWithPets(owner: Int): Optional<Client>

    /*
    // A query that loads all Clients with prefetching of the appointments associated
    @Query("select c from Client p left join fetch c.appointments where c.id = :id")
    fun findByIdWithAppointment(id: Int): Optional<Client>
    */

    @Query("select c from Client c left join fetch c.appointments where c.id = :id")
    fun findByIdWithAppointments(@Param("id") id: Int): Optional<Client>
}
