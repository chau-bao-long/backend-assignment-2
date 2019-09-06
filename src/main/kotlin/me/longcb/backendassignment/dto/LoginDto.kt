package me.longcb.backendassignment.dto

import java.io.Serializable

data class LoginDto(var accessToken: String? = null, var message: String? = null) : Serializable
