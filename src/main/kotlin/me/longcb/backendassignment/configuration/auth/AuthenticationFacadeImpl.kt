package me.longcb.backendassignment.configuration.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class AuthenticationFacadeImpl : AuthenticationFacade {
    override fun currentUser(): UserDetails {
        val auth = SecurityContextHolder.getContext().authentication
        return auth?.principal as CurrentUserDetails
    }
}
