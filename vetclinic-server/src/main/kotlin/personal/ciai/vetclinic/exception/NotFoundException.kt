package personal.ciai.vetclinic.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class NotFoundException(msg: String = "Resource not found") : RuntimeException(msg)
