package personal.ciai.vetclinic.repository

import org.springframework.data.repository.CrudRepository
import personal.ciai.vetclinic.model.Pet

interface PetRepository : CrudRepository<Pet, Int>
