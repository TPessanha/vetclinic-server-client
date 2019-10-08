package personal.ciai.vetclinic.service

import personal.ciai.vetclinic.dto.AdministrativeDTO

/**
 *
 */
interface AdministrativeService {

    fun exist(id: String): Boolean

    fun find(id: String): AdministrativeDTO

    fun save(admin: AdministrativeDTO)

    fun delete(id: String): Boolean
}
