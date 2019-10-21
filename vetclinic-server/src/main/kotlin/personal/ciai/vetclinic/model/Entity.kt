package personal.ciai.vetclinic.model

import java.io.Serializable
import personal.ciai.vetclinic.dto.Transferable

abstract class Entity() : Serializable {
    abstract fun toDTO(): Transferable
}
