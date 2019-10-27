package personal.ciai.vetclinic.service

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.NotificationDTO
import personal.ciai.vetclinic.model.Notification
import personal.ciai.vetclinic.repository.NotificationRepository

@Service
class NotificationService(
    @Autowired private val notificationRepository: NotificationRepository,
    @Autowired private val clientService: ClientService
) {

    fun getAllNotification(clientId: Int): List<NotificationDTO> {
        clientService.getClientById(clientId)
        return notificationRepository.findAllByClient(clientId).get().map { it.toDTO() }
    }

    fun getAllNewNotification(clientId: Int): List<NotificationDTO> {
        return notificationRepository.findAllByClientAndReaded(
            clientService.getClientEntityById(clientId), false
        ).get().map { it.toDTO() }
    }

    fun getNotification(notificationId: Int): NotificationDTO {
        val not = notificationRepository.findById(notificationId)
            .orElseThrow { NotFoundException("Notification with Id $notificationId not found") }
        return not.toDTO()
    }

    fun getNotificationEntity(notificationId: Int): Notification {
        return notificationRepository.findById(notificationId)
            .orElseThrow { NotFoundException("Notification with Id $notificationId not found") }
    }

    fun notificationReaded(notificationId: Int) {
        val notification = getNotificationEntity(notificationId)
        notification.readed = true
        notificationRepository.save(notification)
    }

    fun save(notification: Notification) {
        notificationRepository.save(notification)
    }
}
