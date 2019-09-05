package me.longcb.backendassignment.controller

import JwtService
import me.longcb.backendassignment.dto.LoginDto
import me.longcb.backendassignment.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Scope("request")
@RequestMapping(value = ["/sessions"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController : BaseController() {
    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var userService: UserService

    @PostMapping
    fun login(): ResponseEntity<LoginDto> {
        val dto = LoginDto()
        dto.jwtToken = "123123123"
        dto.message = "dddaaaa"
        return ResponseEntity(dto, HttpStatus.OK)
    }
}
