package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.StaffEntity

interface StaffService {
    fun getSuperiors(staffName: String): List<StaffEntity>
}
