package personal.ciai.vetclinic.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import personal.ciai.vetclinic.service.UserService

class CustomUserDetails(
        private val aUsername:String,
        private val aPassword:String,
        private val someAuthorities:MutableCollection<out GrantedAuthority>) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = someAuthorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = aUsername

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = aPassword

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}


@Service
class CustomUserDetailsService(
        val users: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {
            val user = users.getUserEntityByUsername(it)
            if( user.isPresent ) {
                return CustomUserDetails(user.get().username, user.get().password, mutableListOf())
            } else
                throw UsernameNotFoundException(username)
        }
        throw UsernameNotFoundException(username)
    }
}
