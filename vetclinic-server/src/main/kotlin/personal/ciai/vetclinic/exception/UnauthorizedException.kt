package personal.ciai.vetclinic.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
class UnauthorizedException(msg: String = "Not authorized to do that operation") : RuntimeException(msg)
