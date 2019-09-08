package me.longcb.backendassignment.controller

import me.longcb.backendassignment.service.HierarchyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Scope("request")
@RequestMapping(value = ["/hierarchy"], produces = [MediaType.APPLICATION_JSON_VALUE])
class HierarchyController : BaseController() {
    @Autowired
    private lateinit var hierarchyService: HierarchyService

    @PostMapping
    fun create(@RequestBody relationships: HashMap<String, String>) {
        hierarchyService.buildRelationship(relationships)
    }

    @GetMapping
    fun getHierarchy(): ResponseEntity<String> =
            ResponseEntity(hierarchyService.buildHierarchy().toJsonString(), HttpStatus.OK)
}
