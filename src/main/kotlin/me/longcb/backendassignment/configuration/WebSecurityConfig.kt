package me.longcb.backendassignment.configuration

import me.longcb.backendassignment.configuration.auth.filter.JwtAuthenticationTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Collections.singletonList
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.config.annotation.web.builders.HttpSecurity


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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = singletonList("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.addAllowedHeader("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .headers()
                .contentTypeOptions().and()
                .xssProtection().and()
                .cacheControl().and()
                .httpStrictTransportSecurity().and()
                .frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/users/seed").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}
