package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.CascadeType
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

    @OneToMany(targetEntity = Appointment::class, cascade = [CascadeType.ALL], orphanRemoval = true)
    var appointments: MutableList<Appointment> = arrayListOf(),

    @OneToMany(
        targetEntity = Schedules::class, mappedBy = "veterinarian", cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val schedules: MutableList<Schedules> = arrayListOf()

) : Employee(id, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO() = VeterinarianDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString(),
        appointments = arrayListOf(),
        enabled = enabled,
        schedules = arrayListOf()
    )
}
