package me.longcb.backendassignment.service

interface JwtService {
    fun generateLoginToken(userId: Long?): String

    fun validateLoginToken(token: String?): Boolean
    
    fun getUserIdFromLoginToken(token: String): Long?
}