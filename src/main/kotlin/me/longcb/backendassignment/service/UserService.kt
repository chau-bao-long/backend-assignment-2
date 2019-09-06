package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.UserEntity
import me.longcb.backendassignment.model.Login

interface UserService {
    fun checkLoginAndGetUser(login: Login): UserEntity?

    fun getUserByName(name: String?): UserEntity?

    fun seedUser()
}
