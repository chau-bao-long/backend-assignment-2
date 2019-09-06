package me.longcb.backendassignment.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "relations")
data class RelationEntity(
        @Column(name = "superior_id", nullable = false)
        var superiorId: Long,
        @Column(name = "subordinate_id", nullable = false)
        var subordinateId: Long
) : AbstractEntity<Long>()