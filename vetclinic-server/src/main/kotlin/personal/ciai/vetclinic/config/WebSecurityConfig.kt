package personal.ciai.vetclinic.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import personal.ciai.vetclinic.security.CustomAccessDeniedHandler
import personal.ciai.vetclinic.security.UserDetailsService
import personal.ciai.vetclinic.service.UserService

@Configuration
private class WebSecurityConfig(
    val userDetails: UserDetailsService,
    val users: UserService,
    @Autowired
    val userService: UserService
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable() // for now, we can disable cross site request forgery protection
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
            .and()
            .authorizeRequests()
            .antMatchers("/v2/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/console/**").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.POST, "/signup").permitAll()
            .antMatchers("/clients/**").hasRole("CLIENT")
            .antMatchers("/clients").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().headers().frameOptions().sameOrigin() // H2CONSOLE
            .and()
            .addFilterBefore(
                UserPasswordAuthenticationFilterToJWT("/login", super.authenticationManagerBean()),
                BasicAuthenticationFilter::class.java
            )
            .addFilterBefore(
                UserPasswordSignUpFilterToJWT("/signup", users),
                BasicAuthenticationFilter::class.java
            )
            .addFilterBefore(
                JWTAuthenticationFilter(userService),
                BasicAuthenticationFilter::class.java
            )

    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler {
        return CustomAccessDeniedHandler()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password(BCryptPasswordEncoder().encode("password"))
            .authorities(emptyList())
            .and()
            .passwordEncoder(BCryptPasswordEncoder())
            .and()
            .userDetailsService(userDetails)
            .passwordEncoder(BCryptPasswordEncoder())
    }
}
