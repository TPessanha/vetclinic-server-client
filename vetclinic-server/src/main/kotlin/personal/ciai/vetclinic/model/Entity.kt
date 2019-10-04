package personal.ciai.vetclinic.model

import java.io.Serializable

abstract class Entity<out DTO>() : Serializable {
    abstract fun toDTO(): DTO
}
