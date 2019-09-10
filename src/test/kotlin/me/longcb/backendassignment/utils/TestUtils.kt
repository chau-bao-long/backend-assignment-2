package me.longcb.backendassignment.utils

import me.longcb.backendassignment.entity.AbstractEntity
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDateTime
import javax.persistence.EntityManager

fun fakeAuditTime(vararg entities: AbstractEntity<out Long>) {
    entities.forEach { e ->
        e.createdAt = LocalDateTime.now()
        e.updatedAt = LocalDateTime.now()
    }
}

fun persist(em: TestEntityManager, vararg entities: AbstractEntity<out Long>) {
    entities.forEach { e -> em.persist(e) }
}
fun persist(em: EntityManager, vararg entities: AbstractEntity<out Long>) {
    entities.forEach { e -> em.persist(e) }
}
