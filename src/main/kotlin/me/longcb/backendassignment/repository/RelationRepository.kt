package me.longcb.backendassignment.repository

import me.longcb.backendassignment.entity.RelationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RelationRepository : JpaRepository<RelationEntity, Long>
