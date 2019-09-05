package me.longcb.backendassignment.model

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractModel<T> {
    @Id
    @GeneratedValue
    private var id: T? = null

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private var createdAt: LocalDateTime? = null

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private var createdBy: Long? = null

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private var updatedAt: LocalDateTime? = null

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private var updatedBy: Long? = null
}
