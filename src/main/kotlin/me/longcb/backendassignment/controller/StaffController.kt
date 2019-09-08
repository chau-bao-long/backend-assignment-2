package me.longcb.backendassignment.controller

import me.longcb.backendassignment.dto.StaffDto
import me.longcb.backendassignment.service.StaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Scope("request")
@RequestMapping(value = ["/staffs"], produces = [MediaType.APPLICATION_JSON_VALUE])
class StaffController : BaseController() {
    @Autowired
    private lateinit var staffService: StaffService

    @GetMapping("/{name}/superiors")
    fun getSuperiors(@PathVariable("name") staffName: String): ResponseEntity<List<StaffDto>> {
        val staffs = staffService.getSuperiors(staffName).map { e -> StaffDto(e.name) }
        return ResponseEntity(staffs, HttpStatus.OK)
    }
}