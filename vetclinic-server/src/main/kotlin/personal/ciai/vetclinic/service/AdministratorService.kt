package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.repository.AdministratorRepository

@Service
class AdministratorService(
    @Autowired val adminRepository: AdministratorRepository,
    @Autowired val configurationProperties: ConfigurationProperties
) {

    fun existsById(id: Int): Boolean {
        return adminRepository.existsById(id)
    }

    fun getAllAdministrator(): List<AdministratorDTO> = adminRepository.findAll()
        .map { it.toDTO() }

    fun getAdministratorById(id: Int): AdministratorDTO = getAdministratorEntity(id).toDTO()

    fun save(adminDTO: AdministratorDTO) {
        if (existsAdministratorById(adminDTO.id).not())
            adminRepository.save(adminDTO.toEntity(configurationProperties.fullPathToUserPhotos))
        else throw ConflictException("Administrator account with Id ${adminDTO.id} already exist")
    }

    fun update(adminDTO: AdministratorDTO) {
        val admin: Administrator = getAdministratorEntity(adminDTO.id)

        adminRepository.save(adminDTO.toEntity(admin.id, configurationProperties.fullPathToUserPhotos))
    }

    fun delete(id: Int) {
        val admin: Administrator = getAdministratorEntity(id)

        adminRepository.delete(admin)
    }

    private fun existsAdministratorById(id: Int): Boolean = adminRepository.existsById(id)

    private fun getAdministratorEntity(id: Int): Administrator = adminRepository.findById(id)
        .orElseThrow { NotFoundException("Administrator account with Id $id not found") }

    fun administradorEntityByEmployeeId(id: Int): Administrator = adminRepository.getAdministratorByEmployeeId(id)
        .orElseThrow { NotFoundException("Administrator account with Id $id not found") }
}
