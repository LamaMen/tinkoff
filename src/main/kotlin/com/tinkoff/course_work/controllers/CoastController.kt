package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.services.CoastService
import com.tinkoff.course_work.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/coast")
class CoastController(private val userService: UserService, private val coastService: CoastService) {
    private var logger = LoggerFactory.getLogger(CoastController::class.java)

    @GetMapping
    suspend fun getAllCoasts(principal: Principal): List<Coast> {
        logger.info("Отдали все расходы")
        val userId = userService.decodeId(principal)
        return coastService.getAllCoasts(userId)
    }

    @GetMapping("/{id}")
    suspend fun getEmployeeById(principal: Principal, @PathVariable id: Int): Coast {
        logger.info("Отдали расход с id $id")
        val userId = userService.decodeId(principal)
        return coastService.getById(id, userId)
    }

    @PostMapping
    suspend fun addCoastNow(principal: Principal, @RequestBody coast: Coast): Coast {
        logger.info("Добавили расход ${coast.title}")
        val userId = userService.decodeId(principal)
        return coastService.addCoastNow(coast, userId)
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody coast: Coast): Coast {
        logger.info("Обновили расход ${coast.title}")
        val userId = userService.decodeId(principal)
        return coastService.updateCoast(id, coast, userId)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
        logger.info("Удалили расход с id $id")
        val userId = userService.decodeId(principal)
        coastService.deleteCoastById(id, userId)
    }
}