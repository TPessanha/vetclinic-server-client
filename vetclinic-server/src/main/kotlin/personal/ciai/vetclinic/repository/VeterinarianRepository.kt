package personal.ciai.vetclinic.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Veterinarian

@Repository
interface VeterinarianRepository : CrudRepository<Veterinarian, Int>
