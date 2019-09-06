package me.longcb.backendassignment.configuration.auth

import org.springframework.security.core.userdetails.UserDetails

interface AuthenticationFacade {
    fun currentUser(): UserDetails
}