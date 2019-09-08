package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.StaffEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StaffRepository : JpaRepository<StaffEntity, Long> {
}
