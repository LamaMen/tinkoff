package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.services.CoastService
import com.tinkoff.course_work.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/coast")
class CoastController(val userService: UserService, val coastService: CoastService) {
    @GetMapping
    fun getAllCoasts(): List<Coast> {
        val user = userService.getUserByLogin("admin")
        return coastService.getAllCoasts(user)
    }
}