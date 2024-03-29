package me.longcb.backendassignment.controller

import me.longcb.backendassignment.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@Scope("request")
@RequestMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController : BaseController() {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/seed")
    @ResponseStatus(value = HttpStatus.OK)
    fun seed() {
        userService.seedUser()
    }
}
