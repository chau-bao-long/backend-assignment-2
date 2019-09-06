package me.longcb.backendassignment.controller

import JwtService
import me.longcb.backendassignment.dto.LoginDto
import me.longcb.backendassignment.model.Login
import me.longcb.backendassignment.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Scope("request")
@RequestMapping(value = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController : BaseController() {
    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var userService: UserService

    @GetMapping(value = ["/test"])
    fun test() = "dfd"

    @PostMapping
    fun login(@RequestBody login: Login): ResponseEntity<LoginDto> {
        val dto = LoginDto()
        lateinit var httpStatus: HttpStatus
        val user = userService.checkLoginAndGetUser(login)
        if (user != null) {
            dto.jwtToken = jwtService.generateLoginToken(user.id)
            dto.message = LOGIN_SUCCESS
            httpStatus = HttpStatus.OK
        } else {
            dto.message = LOGIN_FAIL
            httpStatus = HttpStatus.BAD_REQUEST
        }
        return ResponseEntity(dto, httpStatus)
    }

    companion object {
        const val LOGIN_SUCCESS = "Login success"
        const val LOGIN_FAIL = "Invalid email or password"
    }
}
