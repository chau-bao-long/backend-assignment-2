package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.model.TreeNode
import me.longcb.backendassignment.repository.RelationRepository
import me.longcb.backendassignment.repository.StaffRepository
import me.longcb.backendassignment.service.HierarchyService
import me.longcb.backendassignment.service.TreeBuilderService
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

    override fun buildRelationship(relationships: HashMap<String, String>): TreeNode<String> {
        val (staffs, relations) = buildEntity(relationships)
        val tree = TreeBuilderService(staffs, relations).build()
        replaceHierarchy(staffs, relations)
        return tree
    }

    override fun buildHierarchy(): TreeNode<String> =
            TreeBuilderService(staffRepository.findAll(), relationRepository.findAll()).build()

    @Transactional
    override fun replaceHierarchy(staffs: List<StaffEntity>, relations: List<RelationEntity>) {
        relationRepository.deleteAll()
        staffRepository.deleteAll()
        staffRepository.saveAll(staffs)
        relationRepository.saveAll(relations)
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
}
