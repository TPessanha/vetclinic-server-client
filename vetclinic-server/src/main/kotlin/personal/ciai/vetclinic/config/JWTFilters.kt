package personal.ciai.vetclinic.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.lang.IllegalArgumentException
import java.util.Base64
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.web.filter.GenericFilterBean
import personal.ciai.vetclinic.dto.UserDTO
import personal.ciai.vetclinic.exception.ConflictException
import personal.ciai.vetclinic.model.User
import personal.ciai.vetclinic.service.UserService

object JWTSecret {
    private const val passphrase = "este é um grande segredo que tem que ser mantido escondido"
    val KEY: String = Base64.getEncoder().encodeToString(passphrase.toByteArray())
    const val SUBJECT = "JSON Web Token for CIAI 2019/20"
    val VALIDITY = TimeUnit.MINUTES.toMillis(10).toInt() // 10 minutos in milliseconds
}

private fun addResponseToken(authentication: Authentication, response: HttpServletResponse) {

    val claims = HashMap<String, Any?>()
    claims["username"] = authentication.name
    claims["authorities"] = authentication.authorities.toString()

    val token = Jwts
        .builder()
        .setClaims(claims)
        .setSubject(JWTSecret.SUBJECT)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + JWTSecret.VALIDITY))
        .signWith(SignatureAlgorithm.HS256, JWTSecret.KEY)
        .compact()

    response.addHeader("Authorization", "Bearer $token")
}

class UserPasswordAuthenticationFilterToJWT(
    defaultFilterProcessesUrl: String?,
    private val anAuthenticationManager: AuthenticationManager,
    userService: UserService
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication? {
        // getting user from request body
        val user = ObjectMapper().readValue(request!!.inputStream, User::class.java)

        // perform the "normal" authentication
        val auth =
            anAuthenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))

        return if (auth.isAuthenticated) {
            // Proceed with an authenticated user
            SecurityContextHolder.getContext().authentication = auth
            auth
        } else
            null
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain?,
        auth: Authentication
    ) {

        // When returning from the Filter loop, add the token to the response
        addResponseToken(auth, response)
    }
}

class UserAuthToken(private var login: String, private var authorities: MutableList<SimpleGrantedAuthority>) :
    Authentication {

    override fun getAuthorities() = authorities

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getName() = login

    override fun getCredentials() = null

    override fun getPrincipal() = this

    override fun isAuthenticated() = true

    override fun getDetails() = login
}

class JWTAuthenticationFilter(val userService: UserService) : GenericFilterBean() {
    // To try it out, go to https://jwt.io to generate custom tokens, in this case we only need a name...

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {

        val authHeader = (request as HttpServletRequest).getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7) // Skip 7 characters for "Bearer "

            try {
                val claims = Jwts.parser().setSigningKey(JWTSecret.KEY).parseClaimsJws(token).body

                val auhts = userService.getAuthorities(claims["username"] as String)
                val authentication = UserAuthToken(claims["username"] as String, auhts)
                // Can go to the database to get the actual user information (e.g. authorities)

                SecurityContextHolder.getContext().authentication = authentication

                // Renew token with extended time here. (before doFilter)
                addResponseToken(authentication, response as HttpServletResponse)

                chain!!.doFilter(request, response)
            } catch (e: ExpiredJwtException) {
                (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
                chain!!.doFilter(request, response)
            }
        } else {
//            (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
            chain!!.doFilter(request, response) // TODO: Replace with an error
        }
    }
}

/**
 * Instructions:
 *
 * http POST :8080/login username=user password=password
 *
 * Observe in the response:
 *
 * HTTP/1.1 200
 * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgQ0lBSSAyMDE5LzIwIiwiZXhwIjoxNTcxNzc2MTM4LCJpYXQiOjE1NzE3NDAxMzgsInVzZXJuYW1lIjoidXNlciJ9.Mz18cn5xw-7rBXw8KwlWxUDSsfNCqlliiwoIpvYPDzk
 *
 * http :8080/pets Authorization:"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgQ0lBSSAyMDE5LzIwIiwiZXhwIjoxNTcxNzc2MTM4LCJpYXQiOjE1NzE3NDAxMzgsInVzZXJuYW1lIjoidXNlciJ9.Mz18cn5xw-7rBXw8KwlWxUDSsfNCqlliiwoIpvYPDzk"
 *
 */

class UserPasswordSignUpFilterToJWT(
    defaultFilterProcessesUrl: String?,
    private val users: UserService
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication? {
        // getting user from request body
        val user = ObjectMapper().readValue(request!!.inputStream, User::class.java)

        try {
            return users
                .addUser(user.toDTO() as UserDTO)
                .orElse(null)
                .let {
                    val auth = UserAuthToken(user.username, mutableListOf(SimpleGrantedAuthority("CLIENT")))
                    SecurityContextHolder.getContext().authentication = auth
                    auth
                }
        } catch (e: ConflictException) {
            (response as HttpServletResponse).sendError(HttpServletResponse.SC_CONFLICT)
            return null
        } catch (e: IllegalArgumentException) {
            (response as HttpServletResponse).sendError(HttpServletResponse.SC_EXPECTATION_FAILED)
            return null
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain?,
        auth: Authentication
    ) {

        addResponseToken(auth, response)
    }
}
