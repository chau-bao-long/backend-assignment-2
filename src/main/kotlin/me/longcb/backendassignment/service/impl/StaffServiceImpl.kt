package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.entity.StaffEntity
import me.longcb.backendassignment.repository.SuperiorRepository
import me.longcb.backendassignment.service.StaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class StaffServiceImpl : StaffService {
    @Autowired
    private lateinit var superiorRepository: SuperiorRepository

    override fun getSuperiors(staffName: String): List<StaffEntity> =
            superiorRepository.getSuperiors(staffName)
}
