package me.longcb.backendassignment.exception

import org.springframework.http.HttpStatus

data class ApiError(var status: HttpStatus, var message: String?, var errors: List<String>?) {
    constructor(status: HttpStatus, message: String?, error: String) : this(status, message, listOf(error))
}
