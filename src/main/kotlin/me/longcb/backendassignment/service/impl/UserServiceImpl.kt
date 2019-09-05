package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.model.User
import me.longcb.backendassignment.repository.UserRepository
import me.longcb.backendassignment.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class UserServiceImpl: UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun seedUser() {

    }

    override fun checkLogin(): Boolean {
        return true
    }

    override fun getUserByName(name: String?): User {
        return userRepository.findByName(name)
    }
}
