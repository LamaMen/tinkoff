package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.services.CoastService
import com.tinkoff.course_work.services.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/coast")
class CoastController(userService: UserService, val coastService: CoastService) {
    private val user = userService.getUserByLogin("admin")

    @GetMapping
    fun getAllCoasts(): List<Coast> {
        return coastService.getAllCoasts(user)
    }

    @PostMapping
    fun addCoastNow(@RequestBody coast: Coast): Coast {
        return coastService.addCoastNow(coast, user)
    }
}