package me.longcb.backendassignment.exception

import org.springframework.http.HttpStatus

class CustomException(
        val errorCode: ErrorCode,
        override val message: String?,
        val httpCode: HttpStatus = HttpStatus.BAD_REQUEST
) : RuntimeException()
