package me.longcb.backendassignment.service.impl

import me.longcb.backendassignment.service.StaffService
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class StaffServiceImpl: StaffService {
    override fun getSuperiors() {
    }
}
