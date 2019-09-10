package me.longcb.backendassignment.service

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.exception.CustomException
import me.longcb.backendassignment.exception.ErrorCode
import me.longcb.backendassignment.model.TreeNode

class TreeBuilderService(private val staffs: List<StaffEntity>, private val relations: List<RelationEntity>) {
    private var positionMap: HashMap<String, Int>

    private var matrix: Array<IntArray>

    init {
        if (staffs.isEmpty() || relations.isEmpty()) throw CustomException(ErrorCode.HIERARCHY_EMPTY, "Hierarchy is empty")
        this.positionMap = staffs.foldIndexed(HashMap()) { index, acc, item -> acc[item.name] = index; acc }
        this.matrix = Array(staffs.size) { IntArray(staffs.size) }
    }

    fun build(): TreeNode<String> {
        buildMatrix(relations)
        val root = findRoot()
        return buildTree(root)
    }

    private fun buildTree(root: Int): TreeNode<String> {
        val node = TreeNode(staffName(root))
        findSubordinateIndexes(root).forEach { subordinateIndex -> node.addChild(buildTree(subordinateIndex)) }
        return node
    }

    private fun buildMatrix(relations: List<RelationEntity>) {
        relations.forEach { relation ->
            val superiorIndex = positionMap[relation.superior]!!
            val superSuperiors = findSuperiorIndexes(superiorIndex)
            fillMatrix(superiorIndex, superSuperiors, intArrayOf(positionMap[relation.subordinate]!!))
        }
    }

    private fun fillMatrix(superiorIndex: Int, superSuperiors: List<Int>, subordinateIndexes: IntArray) {
        superSuperiors.forEach { s -> matrix[s][subordinateIndexes.last()] += 2 }
        matrix[superiorIndex][subordinateIndexes.last()] += 1
        findSubordinateIndexes(subordinateIndexes.last()).forEach { subordinateIndex ->
            if (subordinateIndex == superiorIndex) raiseConflict(superiorIndex, subordinateIndexes + intArrayOf(subordinateIndex))
            matrix[superiorIndex][subordinateIndex] += 1
            fillMatrix(superiorIndex, superSuperiors, subordinateIndexes + intArrayOf(subordinateIndex))
        }
    }

    private fun findRoot(): Int {
        val root = matrix.foldIndexed(ArrayList<Int>()) { index, acc, sub_axis ->
            if (sub_axis.count { sub -> sub == 0 } == 1) acc.add(index)
            acc
        }
        if (root.size != 1) throw CustomException(ErrorCode.HIERARCHY_MULTI_ROOT, "contain multiple root")
        return root.first()
    }

    @Throws(CustomException::class)
    private fun raiseConflict(superiorIndex: Int, subordinateIndexes: IntArray) {
        val errorMessage = if (subordinateIndexes.all { sub -> sub == superiorIndex }) {
            "${staffName(superiorIndex)} cannot be superior for himself/herself"
        } else {
            """
           ${staffName(superiorIndex)} cannot be supervisor of ${staffName(subordinateIndexes[0])}, because
           ${staffName(subordinateIndexes[0])} is supervisor of ${staffName(subordinateIndexes[1])},
           ${(2 until subordinateIndexes.size).fold("") { a, i ->
                a + "who is supervisor of ${staffName(subordinateIndexes[i])}, "
            }}
            """.trimIndent()
        }
        throw CustomException(ErrorCode.HIERARCHY_CONFLICT, errorMessage)
    }

    private fun findSubordinateIndexes(superiorIndex: Int) =
            matrix[superiorIndex].foldIndexed(ArrayList<Int>()) { index, acc, sub -> if (sub == 1) acc.add(index); acc }

    private fun findSuperiorIndexes(subordinateIndex: Int) =
            matrix.foldIndexed(ArrayList<Int>()) { index, acc, sup ->
                if (sup[subordinateIndex] > 0) acc.add(index)
                acc
            }

    private fun staffName(index: Int) = staffs[index].name
}
