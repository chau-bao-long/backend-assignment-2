package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByName(name: String?): UserEntity?
}
