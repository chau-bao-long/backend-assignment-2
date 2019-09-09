package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDateTime

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var repository: UserRepository

    @Test
    fun test() {
        val userEntity = UserEntity("long", "123456")
        userEntity.createdAt = LocalDateTime.now()
        userEntity.updatedAt = LocalDateTime.now()
        this.entityManager.persist<UserEntity>(userEntity)
        val user = this.repository.findByName("long")
        assertThat(user?.name).isEqualTo("long")
        assertThat(user?.encryptedPassword).isEqualTo("123456")
    }
}