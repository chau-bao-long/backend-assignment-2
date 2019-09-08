package me.longcb.backendassignment.model

class TreeNode<T>(var value: T) {
    private var parent: TreeNode<T>? = null

    private var children: MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(node: TreeNode<T>) {
        children.add(node)
        node.parent = this
    }

    fun toJsonString() = "{ ${toString()} }"

    override fun toString(): String {
        val childValue = children.joinToString(", ") { it.toString() }
        return "\"$value\": { $childValue }"
    }
}