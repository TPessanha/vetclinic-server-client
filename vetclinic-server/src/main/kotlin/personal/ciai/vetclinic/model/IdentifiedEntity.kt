package personal.ciai.vetclinic.model

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class IdentifiedEntity<DTO>(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int
) : Entity<DTO>()
