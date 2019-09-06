package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.UserEntity
import me.longcb.backendassignment.model.Login

interface UserService {
    fun checkLoginAndGetUser(login: Login): UserEntity?

    fun getUserById(id: Long?): UserEntity?

    fun seedUser()
}
