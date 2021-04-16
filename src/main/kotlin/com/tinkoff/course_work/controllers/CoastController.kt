package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.services.CoastService
import com.tinkoff.course_work.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/coast")
class CoastController(userService: UserService, val coastService: CoastService) {
    private var logger = LoggerFactory.getLogger(CoastController::class.java)
    private val user = userService.getUserByLogin("admin")

    @GetMapping
    fun getAllCoasts(): List<Coast> {
        logger.info("Отдали все расходы")
        return coastService.getAllCoasts(user)
    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Int): Coast {
        logger.info("Отдали расход с id $id")
        return coastService.getById(id, user)
    }

    @PostMapping
    fun addCoastNow(@RequestBody coast: Coast): Coast {
        logger.info("Добавили расход ${coast.title}")
        return coastService.addCoastNow(coast, user)
    }
}