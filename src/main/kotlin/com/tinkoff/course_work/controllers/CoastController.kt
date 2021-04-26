package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.services.CoastService
import com.tinkoff.course_work.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/coast")
class CoastController(private val userService: UserService, private val coastService: CoastService) {
    private var logger = LoggerFactory.getLogger(CoastController::class.java)

    @GetMapping
    suspend fun getAllCoasts(): List<Coast> {
        logger.info("Отдали все расходы")
        val user = userService.getUserByLogin("admin")
        return coastService.getAllCoasts(user)
    }

    @GetMapping("/{id}")
    suspend fun getEmployeeById(@PathVariable id: Int): Coast {
        logger.info("Отдали расход с id $id")
        val user = userService.getUserByLogin("admin")
        return coastService.getById(id, user)
    }

    @PostMapping
    suspend fun addCoastNow(@RequestBody coast: Coast): Coast {
        logger.info("Добавили расход ${coast.title}")
        val user = userService.getUserByLogin("admin")
        return coastService.addCoastNow(coast, user)
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(@PathVariable id: Int, @RequestBody coast: Coast): Coast {
        logger.info("Обновили расход ${coast.title}")
        val user = userService.getUserByLogin("admin")
        return coastService.updateCoast(id, coast, user)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(@PathVariable id: Int) {
        logger.info("Удалили расход с id $id")
        val user = userService.getUserByLogin("admin")
        coastService.deleteCoastById(id, user)
    }
}