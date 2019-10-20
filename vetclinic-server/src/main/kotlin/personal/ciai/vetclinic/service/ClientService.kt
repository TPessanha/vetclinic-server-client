package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.dto.ClientDTO
import personal.ciai.vetclinic.exception.ExpectationFailedException
import personal.ciai.vetclinic.exception.NotFoundException
// import personal.ciai.vetclinic.model.Appointment
import personal.ciai.vetclinic.model.Client
import personal.ciai.vetclinic.repository.ClientRepository

@Service
class ClientService(

    @Autowired
    val repository: ClientRepository,
    @Autowired
    val imageService: ImageService
) {

    companion object MediaTypes {

        val imageTypes = listOf(
            MediaType.IMAGE_JPEG.toString(),
            MediaType.IMAGE_PNG.toString()
        )
    }
    /*
    fun checkAppointments(id: Int): List<Appointment> {
        val client =
            repository.findByIdWithAppointment(id)
                .orElseThrow { NotFoundException("Pet with id ($id) not found") }
        return client.appointments
    }
    */

    fun getClientById(id: Int) = getClientEntityById(id).toDTO()

    fun getClientEntityById(id: Int): Client {
        val client = repository.findById(id)
        if (client.isPresent)
            return client.get()
        else
            throw NotFoundException("Client with id ($id) not found")
    }

    fun saveClient(clientDTO: ClientDTO, id: Int = 0) {
        if (id > 0 && !repository.existsById(clientDTO.id))
            throw NotFoundException("Pet with id (${clientDTO.id}) not found")

        val pet = clientDTO.toEntity(id)

        if (pet.id != 0)
            throw ExpectationFailedException("Id must be 0 in insertion or > 0 for update")

        repository.save(pet)
    }

    fun getPhoto(id: Int): ByteArray {
        val client = getClientEntityById(id)
        return imageService.getClientPhoto(client)
    }

    fun updatePhoto(id: Int, photo: MultipartFile) {
        val client = getClientEntityById(id)
        val newClient = imageService.updateClientPhoto(client, photo)
        saveClient(newClient.toDTO(), newClient.id)
    }

    fun getClientWithPets(id: Int): Client =
        repository.findByIdWithPets(id)
            .orElseThrow { NotFoundException("Client with id ($id) not found") }
}
