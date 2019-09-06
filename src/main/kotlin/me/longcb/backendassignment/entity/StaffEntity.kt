package me.longcb.backendassignment.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "staffs")
data class StaffEntity(
        @Column(name = "name", unique = true, updatable = false)
        var name: String
) : AbstractEntity<Long>()