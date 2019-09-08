package me.longcb.backendassignment.service

import me.longcb.backendassignment.model.TreeNode

interface HierarchyService {
    fun buildRelationship(relationships: HashMap<String, String>)

    fun buildHierarchy(): TreeNode<String>

    fun cleanHierarchy()
}
