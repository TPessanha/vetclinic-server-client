package personal.ciai.vetclinic.service

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.model.Notification
import personal.ciai.vetclinic.repository.NotificationRepository

@Service
class NotificationService(
    @Autowired private val notificationRepository: NotificationRepository,
    @Autowired private val clientService: ClientService
) {

    fun getAllNotification(clientId: Int): List<Notification> {
        clientService.getClientById(clientId)
        return notificationRepository.findAllByClient(clientId).get()
    }

    fun getAllNewNotification(clientId: Int): List<Notification> {
        return notificationRepository.findAllByClientAndReaded(
            clientService.getClientEntityById(clientId), false
        ).get()
    }

    fun getNotification(notificationId: Int): Notification {
        return notificationRepository.findById(notificationId)
            .orElseThrow { NotFoundException("Notification with Id $notificationId not found") }
    }

    fun notificationReaded(notificationId: Int) {
        val notitication = getNotification(notificationId)
        notitication.readed = true
        notificationRepository.save(notitication)
    }

    fun save(notification: Notification) {
        notificationRepository.save(notification)
    }
}
