package personal.ciai.vetclinic.repository

import java.util.Date
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Schedules
import personal.ciai.vetclinic.model.Veterinarian

@Repository
interface SchedulesRepository : JpaRepository<Schedules, Int> {

    fun getByVeterinarianAndStartDate(veterinarian: Veterinarian, startDate: Date): Optional<Schedules>

    fun findAllByVeterinarianAndStartDateIsGreaterThanEqual(
        veterinarian: Veterinarian,
        startDate: Date
    ): List<Schedules>
}
