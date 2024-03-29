package me.longcb.backendassignment.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ApiErrorHandler : ResponseEntityExceptionHandler() {
    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "No handler found for ${ex.httpMethod} ${ex.requestURL}"
        val apiError = ApiError(ErrorCode.UNCAUGHT_EXCEPTION, HttpStatus.NOT_FOUND, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "${ex.parameterName} parameter is missing"
        val apiError = ApiError(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ex.errorCode, ex.httpCode, ex.localizedMessage, ex.message ?: "")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ErrorCode.INVALID_PARAMS, HttpStatus.BAD_REQUEST, ex.localizedMessage, ex.message)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ErrorCode.INVALID_PARAMS, HttpStatus.BAD_REQUEST, ex.localizedMessage, ex.message ?: "")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ErrorCode.INVALID_PARAMS, HttpStatus.BAD_REQUEST, ex.localizedMessage, ex.message ?: "")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ErrorCode.UNCAUGHT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, "error occurred")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }
}
