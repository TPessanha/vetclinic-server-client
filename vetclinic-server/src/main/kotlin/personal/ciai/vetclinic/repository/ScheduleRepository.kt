package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Schedule

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Int>
