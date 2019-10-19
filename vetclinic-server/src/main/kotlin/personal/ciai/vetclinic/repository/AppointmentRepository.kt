package personal.ciai.vetclinic.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Appointment

@Repository
interface AppointmentRepository : CrudRepository<Appointment, Int> {
//    @Query("SELECT * FROM appointments a WHERE a.PET_ID = :id", nativeQuery = true)
//    fun findAppointmentsByPetId(@Param("id") id: Int): List<Appointment>

    fun findByPetId(id: Int): List<Appointment>

//    @Query("SELECT * FROM appointments a WHERE a.CLIENT_ID = :id", nativeQuery = true)
//    fun findAppointmentsByClientId(id: Int): List<Appointment> = TODO("WAIT FOR CLIENTS IMPLEMENTATION")
}
