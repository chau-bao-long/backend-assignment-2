package me.longcb.backendassignment.controller

import me.longcb.backendassignment.dto.StaffDto
import me.longcb.backendassignment.dto.SuperiorDto
import me.longcb.backendassignment.service.StaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotBlank

@RestController
@Scope("request")
@RequestMapping(value = ["/staffs"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class StaffController : BaseController() {
    @Autowired
    private lateinit var staffService: StaffService

    @GetMapping("/{name}/superiors")
    fun getSuperiors(@PathVariable("name") @NotBlank staffName: String): ResponseEntity<SuperiorDto> {
        val staffs = staffService.getSuperiors(staffName).map { e -> StaffDto(e.id, e.name) }
        return ResponseEntity(SuperiorDto(staffs), HttpStatus.OK)
    }
}