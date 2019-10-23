package personal.ciai.vetclinic.exception

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
class PreconditionFailedException(msg: String = "Some precondition was not meet") : RuntimeException(msg)
