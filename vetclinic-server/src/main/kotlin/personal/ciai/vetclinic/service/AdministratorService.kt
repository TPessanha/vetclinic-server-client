package personal.ciai.vetclinic.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import personal.ciai.vetclinic.config.ConfigurationProperties
import personal.ciai.vetclinic.dto.AdministratorDTO
import personal.ciai.vetclinic.exception.AccessForbiddenException
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.exception.NotFoundException
import personal.ciai.vetclinic.model.Administrator
import personal.ciai.vetclinic.repository.AdministratorRepository

@Service
class AdministratorService(
    @Autowired val adminRepository: AdministratorRepository,
    @Autowired val configurationProperties: ConfigurationProperties,
    @Autowired val imageService: ImageService
) {

    fun existsById(id: Int): Boolean {
        return adminRepository.existsById(id)
    }

    fun getAllAdministrator(): List<AdministratorDTO> = adminRepository.findAll()
        .map { it.toDTO() }

    fun getAdministratorById(id: Int): AdministratorDTO = getAdministratorEntity(id).toDTO()

    fun save(adminDTO: AdministratorDTO) {
        if (existByUserName(adminDTO.username).not())
            adminRepository.save(adminDTO.toEntity(configurationProperties.fullPathToUserPhotos))
        else throw ConflictException("Administrator account with UserName ${adminDTO.username} already exist")
    }

    fun update(adminDTO: AdministratorDTO) {
        val admin: Administrator = getAdministratorEntity(adminDTO.id)

        adminRepository.save(adminDTO.toEntity(admin.id, configurationProperties.fullPathToUserPhotos))
    }

    fun delete(id: Int) {
        if (id == 1)
            throw AccessForbiddenException("This administrator cannot be deleted")
        val admin: Administrator = getAdministratorEntity(id)

        adminRepository.delete(admin)
    }

    fun existByUserName(userName: String) = adminRepository.existsByUsername(userName)

    fun getAdministratorEntity(id: Int): Administrator = adminRepository.findById(id)
        .orElseThrow { NotFoundException("Administrator account with Id $id not found") }

    fun getPhoto(id: Int): ByteArray {
        val admin = getAdministratorEntity(id)
        return imageService.getUserPhoto(admin.photo)
    }

    fun updatePhoto(id: Int, photo: MultipartFile) {
        val admin = getAdministratorEntity(id)
        admin.photo = imageService.updateUserPhoto(admin.id, photo)
        update(admin.toDTO())
    }
}
