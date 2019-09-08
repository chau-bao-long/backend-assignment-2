package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.StaffEntity

interface SuperiorRepository {
    fun getSuperiors(name: String): List<StaffEntity>
}
