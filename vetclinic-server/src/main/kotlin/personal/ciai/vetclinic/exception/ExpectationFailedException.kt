package personal.ciai.vetclinic.exception

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
class ExpectationFailedException(msg: String = "Some precondition was not meet") : RuntimeException(msg)
