package me.longcb.backendassignment.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "relations")
data class RelationEntity(
        @Column(name = "superior", nullable = false)
        var superior: String,
        @Column(name = "subordinate", nullable = false)
        var subordinate: String
) : AbstractEntity<Long>()