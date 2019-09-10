package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.UserEntity
import me.longcb.backendassignment.model.Login
import me.longcb.backendassignment.repository.UserRepository
import me.longcb.backendassignment.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @InjectMocks
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun givenUser_whenRightUserAndPassword_thenReturnUser() {
        val user = UserEntity(userName, password)
        Mockito.`when`(userRepository.findByName(userName)).thenReturn(user)
        Mockito.`when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(true)

        val currentUser = userService.checkLoginAndGetUser(Login(userName, password))
        Assertions.assertEquals(user, currentUser)
    }

    @Test
    fun givenUser_whenRightUserButWrongPassword_thenReturnNull() {
        val user = UserEntity(userName, password)
        Mockito.`when`(userRepository.findByName(userName)).thenReturn(user)
        Mockito.`when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(false)

        val currentUser = userService.checkLoginAndGetUser(Login(userName, password))
        Assertions.assertNull(currentUser)
    }

    @Test
    fun givenUser_whenEmptyUserAndPassword_thenReturnNull() {
        val user1 = userService.checkLoginAndGetUser(Login("", ""))
        val user2 = userService.checkLoginAndGetUser(Login(null, ""))
        val user3 = userService.checkLoginAndGetUser(Login("", null))
        val user4 = userService.checkLoginAndGetUser(Login(null, null))
        Assertions.assertNull(user1)
        Assertions.assertNull(user2)
        Assertions.assertNull(user3)
        Assertions.assertNull(user4)
    }

    companion object {
        const val userName = "abc"
        const val password = "123456"
    }
}
