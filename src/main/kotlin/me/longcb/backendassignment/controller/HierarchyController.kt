package me.longcb.backendassignment.controller

import me.longcb.backendassignment.service.HierarchyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Scope("request")
@RequestMapping(value = ["/hierarchy"], produces = [MediaType.APPLICATION_JSON_VALUE])
class HierarchyController : BaseController() {
    @Autowired
    private lateinit var hierarchyService: HierarchyService

    @PostMapping
    fun create(@RequestBody relations: HashMap<String, String>) {
        hierarchyService.buildRelationship(relations)
    }
}
