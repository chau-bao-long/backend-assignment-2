package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity

interface HierarchyService {
    fun buildRelationship(relation: HashMap<String, String>): Pair<Array<StaffEntity>, Array<RelationEntity>>

    fun buildHierarchy(staffs: Array<StaffEntity>, relations: Array<RelationEntity>): HashMap<String, *>

    fun cleanHierarchy()
}
