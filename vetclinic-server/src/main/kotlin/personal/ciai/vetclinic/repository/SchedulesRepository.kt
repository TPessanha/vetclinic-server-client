//package personal.ciai.vetclinic.repository
//
//import java.util.Optional
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import org.springframework.stereotype.Repository
//import personal.ciai.vetclinic.model.Schedules
//
//@Repository
//interface SchedulesRepository : JpaRepository<Schedules, Int> {
//
//    @Query(
//        "select s from Schedules s left join fetch s.veterinarian" +
//                " where s.veterinarian = :veterinarian and  s.timeSlot.startDate = :date"
//    )
//    fun getVeterinarianAndStartDateIsEqual(
//        @Param("veterinarian") veterinarian: Int,
//        @Param("date") date: Number
//    ): Optional<Schedules>
//
//    @Query(
//        "select s from Schedules s left join fetch s.veterinarian" +
//                " where s.veterinarian = :veterinarian and  s.timeSlot.startDate <= :startDate"
//    )
//    fun findAllByVeterinarianAndStartDateIsGreaterThanEqual(
//        @Param("veterinarian") veterinarian: Int,
//        @Param("startDate") startDate: Number
//    ): List<Schedules>
//}
