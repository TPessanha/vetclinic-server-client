package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.repository.ClientRepository

@Service
class ClientService(

    @Autowired
    val repository: ClientRepository
    // private val configurationProperties: ConfigurationProperties
) {

    companion object MediaTypes { // pensar em adicionar a opcao de foto ao client em vez de user
        val imageTypes = listOf(
            MediaType.IMAGE_JPEG.toString(),
            MediaType.IMAGE_PNG.toString()
        )
    }

    // fun checkAppointments():List<Appointment> {

    fun getClientById(id: Int) = getClientEntityById(id).toDTO()

    fun getClientEntityById(id: Int): Client {
        val client = repository.findById(id)
        if (client.isPresent)
            return client.get()
        else
            throw NotFoundException("Client with id ($id) not found")
    }
}
