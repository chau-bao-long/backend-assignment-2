package me.longcb.backendassignment.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "encrypted_password", nullable = false)
    var encryptedPassword: String
): AbstractEntity<Long>()
