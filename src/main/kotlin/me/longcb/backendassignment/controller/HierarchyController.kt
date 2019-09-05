package me.longcb.backendassignment.controller

import org.springframework.context.annotation.Scope
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Scope("request")
@RequestMapping(value = ["/hierarchy"], produces = [MediaType.APPLICATION_JSON_VALUE])
class HierarchyController : BaseController() {
  @GetMapping("/hi")
  fun sample(): String = "hala ok "
}
