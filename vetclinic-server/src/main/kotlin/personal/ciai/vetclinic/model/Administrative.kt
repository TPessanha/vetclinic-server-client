package personal.ciai.vetclinic.model

import java.net.URI
import javax.persistence.Entity
import javax.persistence.Table
import personal.ciai.vetclinic.dto.AdministrativeDTO

@Entity
@Table(name = "administrative")
class Administrative(
    id: Long,
    email: String,
    name: String,
    phoneNumber: Int,
    username: String,
    password: String,
    address: String,
    photo: URI
) : Employee<AdministrativeDTO>(id, email, name, phoneNumber, username, password, address, photo) {

    override fun toDTO() = AdministrativeDTO(
        id = id,
        name = name,
        username = username,
        email = email,
        password = "",
        phoneNumber = phoneNumber,
        address = address,
        photo = photo.toString()
    )

    companion object {

        fun fromDto(dto: AdministrativeDTO) = Administrative(
            id = dto.id,
            name = dto.name,
            username = dto.username,
            email = dto.email,
            password = dto.password, // TODO This dont seen very safe
            phoneNumber = dto.phoneNumber,
            address = dto.address,
            photo = URI.create(dto.photo)
        )

        fun fromDto(dto: AdministrativeDTO, dao: Administrative) = Administrative(
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
