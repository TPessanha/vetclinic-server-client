package personal.ciai.vetclinic.model

import java.io.Serializable

interface Entity<out DTO> : Serializable {
    fun toDTO(): DTO
}
