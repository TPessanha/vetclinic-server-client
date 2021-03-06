package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import personal.ciai.vetclinic.dto.VeterinarianDTO

@Entity
@Table(name = "veterinarians")
class Veterinarian(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI,
    var enabled: Boolean,

    @OneToMany(mappedBy = "veterinarian")
    var appointments: MutableList<Appointment> = arrayListOf(),

    employeeId: Int,

    @OneToMany(mappedBy = "veterinarian")
    var schedules: MutableList<Schedule> = arrayListOf()

) : Employee(id, email, name, phoneNumber, username, password, address, employeeId, photo) {

    override fun toDTO() = VeterinarianDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        phoneNumber = phoneNumber,
        address = address,
        employeeId = employeeId,
        enabled = enabled

    )
}
