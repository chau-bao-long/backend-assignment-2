package me.longcb.backendassignment.exception

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

data class ApiError(
        var code: ErrorCode,
        @JsonIgnore var status: HttpStatus,
        var message: String?,
        var errors: List<String>?
) {
    constructor(code: ErrorCode, status: HttpStatus, message: String?, error: String) :
            this(code, status, message, listOf(error))
}
