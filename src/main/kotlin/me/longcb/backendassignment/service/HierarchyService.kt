package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.model.TreeNode

interface HierarchyService {
    fun buildRelationship(relationships: HashMap<String, String>): TreeNode<String>

    fun buildHierarchy(): TreeNode<String>

    fun replaceHierarchy(staffs: List<StaffEntity>, relations: List<RelationEntity>)
}
