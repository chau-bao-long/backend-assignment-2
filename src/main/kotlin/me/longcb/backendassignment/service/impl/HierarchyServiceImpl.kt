package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.entity.RelationEntity
import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.exception.CustomException
import me.longcb.backendassignment.service.HierarchyService
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class HierarchyServiceImpl : HierarchyService {
    override fun buildRelationship(relation: HashMap<String, String>): Pair<Array<StaffEntity>, Array<RelationEntity>> {
        throw CustomException("hahahahaa")
    }

    override fun buildHierarchy(staffs: Array<StaffEntity>, relations: Array<RelationEntity>): HashMap<String, *> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cleanHierarchy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}