package me.longcb.backendassignment.service

import me.longcb.backendassignment.model.User

interface UserService {
    fun checkLogin(): Boolean

    fun getUserByName(name: String?): User

    fun seedUser()
}