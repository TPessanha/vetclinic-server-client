package personal.ciai.vetclinic.exception

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
class ServerErrorException(msg: String = "Some unknown error has occurred") : RuntimeException(msg)
