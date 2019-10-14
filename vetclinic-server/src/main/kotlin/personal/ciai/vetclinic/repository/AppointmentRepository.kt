package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Appointment

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Int> {
    @Query("SELECT * FROM appointments a WHERE a.PET_ID = :id", nativeQuery = true)
    fun findAppointmentsByPetId(@Param("id") id: Int): List<Appointment>

    @Query("SELECT * FROM appointments a WHERE a.CLIENT_ID = :id", nativeQuery = true)
    fun findAppointmentsByClientId(id: Int): List<Appointment> = TODO("WAIT FOR CLIENTS IMPLEMENTATION")
}
