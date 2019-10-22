package personal.ciai.vetclinic.repository

import java.util.Date
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Schedules

@Repository
interface ScheduleRepository : JpaRepository<Schedules, Int> {

    @Query("select s from Schedules s  left join fetch  s.veterinarian v where s.date >= :afterDate and v.id = :vetId")
    fun findAllByDateAfterWithVeterinarian(
        @Param("vetId") vetId: Int,
        @Param("afterDate") afterDate: Date
    ): List<Schedules>

    fun findAllByDateAfter(afterDate: Date): List<Schedules>
}
