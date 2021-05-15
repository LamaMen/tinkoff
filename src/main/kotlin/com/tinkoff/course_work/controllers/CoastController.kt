package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.services.JsonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/coast")
class CoastController(private val coastService: JsonService<Coast>) {
    private val logger = LoggerFactory.getLogger(CoastController::class.java)

    @GetMapping
    suspend fun getAllCoasts(principal: Principal): List<Coast> {
        val userId = principal.name
        val coasts = coastService.getAll(userId)
        logger.info("Given ${coasts.size} coasts for user $userId")
        return coasts
    }

    @GetMapping("/{id}")
    suspend fun getCoastById(principal: Principal, @PathVariable id: Int): Coast {
        val userId = principal.name
        val coast = coastService.getById(id, userId)
        logger.info("Given coast with ID=$id for user $userId")
        return coast
    }

    @GetMapping("/category")
    suspend fun getByCategory(principal: Principal, @RequestParam(name = "name") category: String): List<Coast> {
        val userId = principal.name
        val coasts = coastService.getByCategory(category, userId)
        logger.info("Given ${coasts.size} coasts for category '$category' for user $userId")
        return coasts
    }

    @PostMapping
    suspend fun addCoast(principal: Principal, @RequestBody coast: Coast): Coast {
        val userId = principal.name
        val savedCoast = coastService.add(coast, userId)
        logger.info("Saved coast with ID=${savedCoast.id} for user $userId")
        return savedCoast
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody coast: Coast): Coast {
        val userId = principal.name
        val updatedCoast = coastService.update(id, coast, userId)
        logger.info("Update coast=$updatedCoast for user $userId")
        return updatedCoast
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
        val userId = principal.name
        coastService.deleteById(id, userId)
        logger.info("Coast with ID=$id deleted for user $userId")
    }
}