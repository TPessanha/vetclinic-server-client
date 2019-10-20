package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Veterinarian

@Repository
interface VeterinarianRepository : JpaRepository<Veterinarian, Int> {

    @Query("select v from Veterinarian v left join fetch v.appointments where v.id = :id")
    fun findByIdWithAppointment(@Param("id") id: Int): Optional<Veterinarian>
}
