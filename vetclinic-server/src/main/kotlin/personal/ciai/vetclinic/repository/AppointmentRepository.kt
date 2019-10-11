package personal.ciai.vetclinic.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Appointment

@Repository
interface AppointmentRepository : CrudRepository<Appointment, Int>
