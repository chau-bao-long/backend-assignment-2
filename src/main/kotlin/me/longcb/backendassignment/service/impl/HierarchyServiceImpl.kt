package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.configuration.auth.AuthenticationFacade
import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.exception.CustomException
import me.longcb.backendassignment.exception.ErrorCode
import me.longcb.backendassignment.model.TreeNode
import me.longcb.backendassignment.repository.RelationRepository
import me.longcb.backendassignment.repository.StaffRepository
import me.longcb.backendassignment.service.HierarchyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Scope("prototype")
class HierarchyServiceImpl : HierarchyService {
    @Autowired
    private lateinit var relationRepository: RelationRepository

    @Autowired
    private lateinit var staffRepository: StaffRepository

    private lateinit var positionMap: HashMap<String, Int>

    private lateinit var matrix: Array<IntArray>

    private lateinit var staffs: List<StaffEntity>

    override fun buildRelationship(relationships: HashMap<String, String>) {
        val (staffs, relations) = buildEntity(relationships)
        buildHierarchy(staffs, relations)
        cleanHierarchy()
        staffRepository.saveAll(staffs)
        relationRepository.saveAll(relations)
    }

    override fun buildHierarchy(): TreeNode<String> =
            buildHierarchy(staffRepository.findAll(), relationRepository.findAll())

    @Transactional
    override fun cleanHierarchy() {
        relationRepository.deleteAll()
        staffRepository.deleteAll()
    }

    private fun buildEntity(relationships: HashMap<String, String>): Pair<List<StaffEntity>, List<RelationEntity>> {
        val staffs = HashSet<StaffEntity>()
        val relations = ArrayList<RelationEntity>()
        for ((subordinate, superior) in relationships) {
            staffs.add(StaffEntity(superior))
            staffs.add(StaffEntity(subordinate))
            relations.add(RelationEntity(superior, subordinate))
        }
        return Pair(staffs.toList(), relations)
    }

    private fun buildHierarchy(staffs: List<StaffEntity>, relations: List<RelationEntity>): TreeNode<String> {
        initHierarchy(staffs)
        buildMatrix(relations)
        val root = findRoot()
        return buildTree(root)
    }

    private fun buildTree(root: Int): TreeNode<String> {
        val node = TreeNode(staffName(root))
        findSubordinateIndexes(root).forEach { subordinateIndex -> node.addChild(buildTree(subordinateIndex)) }
        return node
    }

    private fun initHierarchy(staffs: List<StaffEntity>) {
        this.staffs = staffs
        this.positionMap = staffs.foldIndexed(HashMap()) { index, acc, item -> acc[item.name] = index; acc }
        this.matrix = Array(staffs.size) { IntArray(staffs.size) }
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
        if (subordinateIndexes.all { sub -> sub == superiorIndex }) {
            throw CustomException(ErrorCode.HIERARCHY_CONFLICT, "${staffName(superiorIndex)} cannot be superior for himself/herself")
        }
        throw CustomException(ErrorCode.HIERARCHY_CONFLICT, """
           ${staffName(superiorIndex)} cannot be supervisor of ${staffName(subordinateIndexes[0])}, because
           ${staffName(subordinateIndexes[0])} is supervisor of ${staffName(subordinateIndexes[1])},
           ${(2 until subordinateIndexes.size).fold("") { a, i ->
            a + "who is supervisor of ${staffName(subordinateIndexes[i])}, "
        }}
        """.trimIndent())
    }

    private fun findSubordinateIndexes(superiorIndex: Int) =
            matrix[superiorIndex].foldIndexed(ArrayList<Int>()) { index, acc, sub -> if (sub == 1) acc.add(index); acc }

    private fun findSuperiorIndexes(subordinateIndex: Int) =
            matrix.foldIndexed(ArrayList<Int>()) { index, acc, sup ->
                if (sup[subordinateIndex] == 1) acc.add(index)
                acc
            }

    private fun staffName(index: Int) = staffs[index].name
}
