package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.model.Role
import personal.ciai.vetclinic.repository.RoleRepository

@Service
class RoleService(
    @Autowired
    val roleRepository: RoleRepository
) {
    fun getClientRoles(id: Int): List<Role> {
        return roleRepository.findByUser(id)
    }
}
