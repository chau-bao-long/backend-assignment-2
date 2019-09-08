package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.service.StaffService
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class StaffServiceImpl: StaffService {
    override fun getSuperiors(staffName: String): Array<StaffEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
