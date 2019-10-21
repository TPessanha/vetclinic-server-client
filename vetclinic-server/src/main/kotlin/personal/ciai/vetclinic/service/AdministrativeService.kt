package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.AdministrativeDTO
import personal.ciai.vetclinic.exception.AccessForbiddenException
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Administrative
import personal.ciai.vetclinic.repository.AdministrativeRepository

@Service
class AdministrativeService(
    @Autowired val adminRepository: AdministrativeRepository,
    @Autowired private val employeeService: EmployeeService
) {

    fun existsById(id: Int): Boolean {
        if (adminRepository.existsById(id).not()) {
            throw AccessForbiddenException()
        }
        return true
    }

    fun getAllAdministrative(): List<AdministrativeDTO> = adminRepository.findAll()
        .map { it.toDTO() }

    fun getAdministrativeById(id: Int): AdministrativeDTO = getAdministrativeEntity(id).toDTO()

    fun save(adminDTO: AdministrativeDTO) {
        if (existsAdministrativeById(adminDTO.id).not())
            adminRepository.save(adminDTO.toEntity())
        else throw ConflictException("Administrative account with Id ${adminDTO.id} already exist")
    }

    fun update(adminDTO: AdministrativeDTO, id: Int) {
        val admin: Administrative = getAdministrativeEntity(id)

        adminRepository.save(adminDTO.toEntity(admin.id))
    }

    fun delete(id: Int) {
        val admin: Administrative = getAdministrativeEntity(id)

        adminRepository.delete(admin)
    }

    private fun existsAdministrativeById(id: Int): Boolean = adminRepository.existsById(id)

    private fun getAdministrativeEntity(id: Int): Administrative = adminRepository.findById(id)
        .orElseThrow { NotFoundException("Administrative account with Id $id not found") }
}
