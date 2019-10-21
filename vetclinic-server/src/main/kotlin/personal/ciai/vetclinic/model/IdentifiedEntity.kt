package personal.ciai.vetclinic.model

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class IdentifiedEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int
) : Entity()
