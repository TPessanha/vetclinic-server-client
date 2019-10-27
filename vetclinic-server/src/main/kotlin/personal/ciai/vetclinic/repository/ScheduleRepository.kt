package personal.ciai.vetclinic.repository

import org.springframework.data.jpa.repository.Query
import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import personal.ciai.vetclinic.model.Veterinarian
import personal.ciai.vetclinic.model.Schedule

interface ScheduleRepository : CrudRepository<Schedule, Int> {
    fun findByVeterinarianAndYearAndMonth(vet: Veterinarian, year: Int, month: Int): Optional<Schedule>

    @Query("SELECT s FROM Schedule s where s.veterinarian = :vet_id AND s.year = :year AND s.month = :month")
    fun findByVetIdAndYearAndMonth(
        @Param("vet_id") vetId: Int,
        @Param("year") year: Int,
        @Param("month") month: Int
    ): Optional<Schedule>

    @Query("SELECT v.schedules FROM Veterinarian v where v.id = :vetId")
    fun findByVetId(@Param("vetId") vetId: Int): List<Schedule>
}
