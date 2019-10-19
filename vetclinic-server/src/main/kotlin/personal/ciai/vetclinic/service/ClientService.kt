package personal.ciai.vetclinic.service

import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.ClientDTO
import personal.ciai.vetclinic.model.Client

@Service
class ClientService() {
    /*
     fun checkAppointments():List<Appointment> {
         return
     }

    fun bookAppointment()
    */
    fun ClientDTO.toEntity(): Client {
        return Client(
            id = this.id,
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            username = this.username,
            password = this.password,
            address = this.address

        )
    }
}
