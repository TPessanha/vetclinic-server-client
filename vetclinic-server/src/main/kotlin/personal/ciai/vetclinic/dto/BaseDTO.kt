package personal.ciai.vetclinic.dto

import java.io.Serializable

interface BaseDTO<out Entity> : Serializable {
    fun toEntity(): Entity
}
