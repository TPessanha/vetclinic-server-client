package personal.ciai.vetclinic.model

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToMany
import javax.persistence.Table
import personal.ciai.vetclinic.dto.Transferable

@Entity
@Table(name = "roles")
class Role(
    id: Int,
    @Enumerated(EnumType.STRING)
    val name: RoleName,

    @ManyToMany(mappedBy = "roles")
    var users: MutableList<User> = arrayListOf()
) : IdentifiedEntity(id) {
    override fun toDTO(): Transferable {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    fun toAuthority() = "ROLE_${name.name}"

    enum class RoleName {
        ADMIN,
        VET,
        CLIENT
    }

}
