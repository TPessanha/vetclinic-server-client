package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Client

@Repository
interface ClientRepository : CrudRepository<Client, Int> {
    @Query("select c from Client c left join fetch c.pets where c.id = :owner")
    fun findByIdWithPets(owner: Int): Optional<Client>
}
