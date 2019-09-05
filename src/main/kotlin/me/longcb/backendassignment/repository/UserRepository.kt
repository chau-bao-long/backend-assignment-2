package me.longcb.backendassignment.repository

import me.longcb.backendassignment.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String?): User
}
