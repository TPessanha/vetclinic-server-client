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

    @OneToMany(mappedBy = "veterinarian")
    var schedules: MutableList<Schedules> = arrayListOf()

) : Employee(id, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO() = VeterinarianDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "", // TODO n sei muito bem como tratar isto mas vou ver na aula de terca se o stor diz alguma coisa
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString(),
        appointments = arrayListOf(), // TODO se isto é sempre assim mais vale remover
        enabled = enabled,
        schedules = arrayListOf() // TODO mesma coisa
    )
}