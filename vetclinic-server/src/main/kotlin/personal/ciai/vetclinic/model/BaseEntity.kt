package personal.ciai.vetclinic.model

import java.io.Serializable
import personal.ciai.vetclinic.dto.BaseDTO

interface BaseEntity : Serializable {
    fun toDTO(): BaseDTO
}
