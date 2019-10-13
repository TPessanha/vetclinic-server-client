package personal.ciai.vetclinic.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
class UnsupportedMediaTypeException(msg: String = "Server does not support this media type") :
    RuntimeException(msg)
