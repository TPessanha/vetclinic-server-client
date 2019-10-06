package personal.ciai.vetclinic.service

import org.springframework.stereotype.Service
import personal.ciai.vetclinic.dto.AdministrativeDTO
import  org.springframework.transaction.annotation.Transactional

@Service
class AdministrativeServiceImpl : AdministrativeService {

    override fun exist(id: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(id: String): AdministrativeDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun save(admin: AdministrativeDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun delete(id: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
