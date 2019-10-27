package personal.ciai.vetclinic.exception

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
class UnauthorizedException(msg: String = "Not authorized to do that operation") : RuntimeException(msg)
