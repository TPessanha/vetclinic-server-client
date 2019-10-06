package personal.ciai.vetclinic.model

import personal.ciai.vetclinic.dto.AdministrativeDTO
import java.time.Instant
import javax.persistence.*
import javax.persistence.Entity

@Entity
data class Administrative(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val fullName: String? = null,
    val picture: String? = null,
    val email: String? = null,
    val phoneNumber: Int? = null,
    var address: String? = null
)
