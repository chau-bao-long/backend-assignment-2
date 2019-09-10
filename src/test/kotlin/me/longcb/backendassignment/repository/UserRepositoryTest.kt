package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.UserEntity
import me.longcb.backendassignment.utils.fakeAuditTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var repository: UserRepository

    @BeforeEach
    fun createTestUser() {
        val userEntity = UserEntity(rightName, rightPassword)
        fakeAuditTime(userEntity)
        this.entityManager.persist(userEntity)
    }

    @Test
    fun whenFindByRightName_thenReturnUser() {
        val user = this.repository.findByName(rightName)
        assertThat(user?.name).isEqualTo(rightName)
        assertThat(user?.encryptedPassword).isEqualTo(rightPassword)
    }

    @Test
    fun whenFindByWrongName_thenNotFoundUser() {
        val user = this.repository.findByName(wrongName)
        assertThat(user).isNull()
    }

    companion object {
        const val rightName = "long"
        const val wrongName = "top"
        const val rightPassword = "$23E5dkk3jf3dlf3333kss32144j32"
    }
}