package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.entity.UserEntity
import me.longcb.backendassignment.model.Login
import me.longcb.backendassignment.repository.UserRepository
import me.longcb.backendassignment.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun seedUser() {
        val userEntity = UserEntity("personia", passwordEncoder.encode("123456"))
        userRepository.save(userEntity)
    }

    override fun checkLoginAndGetUser(login: Login): UserEntity? {
        if (login.name.isNullOrBlank() || login.password.isNullOrBlank()) return null
        val user = userRepository.findByName(login.name) ?: return null
        if (!passwordEncoder.matches(login.password, user.encryptedPassword)) return null
        return user
    }

    override fun getUserById(id: Long?): UserEntity? =
            if (id != null) userRepository.findByIdOrNull(id) else null
}
