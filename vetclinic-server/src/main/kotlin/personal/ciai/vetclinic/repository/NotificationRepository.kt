package personal.ciai.vetclinic.repository

import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import personal.ciai.vetclinic.model.Notification

@Repository
interface NotificationRepository : JpaRepository<Notification, Int> {

    @Query(
        "select n from Notification n left join fetch n.client" +
                " where s.client = :client"
    )
    fun findAllByClient(@Param("client") client: Int): Optional<List<Notification>>

    @Query(
        "select n from Notification n left join fetch n.client" +
                " where n.client = :client and not n.readed"
    )
    fun findAllNewNotification(@Param("client") client: Int): Optional<List<Notification>>
}
