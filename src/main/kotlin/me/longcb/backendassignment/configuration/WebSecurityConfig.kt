package me.longcb.backendassignment.configuration

import me.longcb.backendassignment.configuration.auth.filter.JwtAuthenticationTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun jwtAuthenticationTokenFilter(): JwtAuthenticationTokenFilter {
        val jwtAuthenticationTokenFilter = JwtAuthenticationTokenFilter()
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager())
        return jwtAuthenticationTokenFilter
    }
}
