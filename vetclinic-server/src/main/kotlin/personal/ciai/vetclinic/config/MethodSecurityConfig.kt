package personal.ciai.vetclinic.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class MethodSecurityConfigurer : GlobalMethodSecurityConfiguration()
