package personal.ciai.vetclinic.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ConflictException(msg: String = "Resource already exists") : RuntimeException(msg)
