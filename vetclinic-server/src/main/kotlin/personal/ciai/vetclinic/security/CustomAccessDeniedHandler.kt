package personal.ciai.vetclinic.security

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler : AccessDeniedHandler {
    private var errorPage: String? = null

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        if (!response.isCommitted) {
            if (this.errorPage != null) {
                request.setAttribute("SPRING_SECURITY_403_EXCEPTION", accessDeniedException)
                response.status = HttpStatus.FORBIDDEN.value()
                val dispatcher = request.getRequestDispatcher(this.errorPage)
                dispatcher.forward(request, response)
            } else {
                response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.reasonPhrase)
            }
        }
    }

    fun setErrorPage(errorPage: String?) {
        if (errorPage != null && !errorPage.startsWith("/")) {
            throw IllegalArgumentException("errorPage must begin with '/'")
        } else {
            this.errorPage = errorPage
        }
    }

    companion object {
        protected val logger = LogFactory.getLog(CustomAccessDeniedHandler::class.java)
    }
}
