package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import personal.ciai.vetclinic.dto.VeterinarianDTO

@Entity
@Table(name = "veterinarian")
class Veterinarian(
    id: Int,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI,
    @OneToMany()
    var appointments: List<Appointment> = emptyList()
) : Employee<VeterinarianDTO>(id, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO() = VeterinarianDTO(
        id = id,
        email = email,
        name = name,
        username = username,
        password = "",
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString(),
        appointments = appointments.map { it.id }
    )

    companion object {

        fun fromDto(dto: VeterinarianDTO) = Veterinarian(
            id = dto.id,
            name = dto.name,
            username = dto.username,
            email = dto.email,
            password = dto.password, // TODO This dont seen very safe
            phoneNumber = dto.phoneNumber,
            address = dto.address,
            photo = URI.create(dto.photo)
        )

        fun fromDto(dto: VeterinarianDTO, dao: Veterinarian) = Veterinarian(
            id = dto.id,
            name = dto.name,
            username = dto.username,
            email = dto.email,
            password = dao.password, // TODO This dont seen very safe
            phoneNumber = dto.phoneNumber,
            address = dto.address,
            photo = URI.create(dto.photo)
        )
    }
}
