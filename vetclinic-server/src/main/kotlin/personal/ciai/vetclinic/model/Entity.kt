package personal.ciai.vetclinic.model

import java.io.Serializable

abstract class Entity<out DTO>(val id: Int) : Serializable {
    abstract fun toDTO(): DTO
}
