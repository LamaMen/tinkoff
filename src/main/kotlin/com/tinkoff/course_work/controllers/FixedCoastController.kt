package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.fixed.FixedCoast
import com.tinkoff.course_work.services.JsonService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fixed_coast")
class FixedCoastController(private val fixedCoastService: JsonService<FixedCoast>) {
    private val logger = LoggerFactory.getLogger(FixedCoastController::class.java)

//    @GetMapping
//    suspend fun getAllCoasts(
//        principal: Principal,
//        @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "d") from: Date?,
//        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d") to: Date?
//    ): List<FixedCoast> {
//        val userId = principal.name
//        val isAll = from == null
//        logger.info(from.toString())
//        val coasts = fixedCoastService.getFromInterval(userId = userId, from = from, to = to, isAll = isAll)
//        logger.info("Given ${coasts.size} fixed coasts for user $userId")
//        return coasts
//    }
//
//    @GetMapping("/{id}")
//    suspend fun getCoastById(principal: Principal, @PathVariable id: Int): FixedCoast {
//        val userId = principal.name
//        val coast = fixedCoastService.getById(id, userId)
//        logger.info("Given fixed coast with ID=$id for user $userId")
//        return coast
//    }
//
//    @GetMapping("/category")
//    suspend fun getByCategory(principal: Principal, @RequestParam(name = "name") category: String): List<FixedCoast> {
//        val userId = principal.name
//        val coasts = fixedCoastService.getByCategory(category, userId)
//        logger.info("Given ${coasts.size} fixed coasts for category '$category' for user $userId")
//        return coasts
//    }
//
//    @PostMapping
//    suspend fun addCoast(principal: Principal, @RequestBody fixedCoast: FixedCoast): FixedCoast {
//        val userId = principal.name
//        val savedCoast = fixedCoastService.add(fixedCoast, userId)
//        logger.info("Saved fixed coast with ID=${savedCoast.id} for user $userId")
//        return savedCoast
//    }
//
//    @PutMapping("/{id}")
//    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody fixedCoast: FixedCoast): FixedCoast {
//        val userId = principal.name
//        val updatedCoast = fixedCoastService.update(id, fixedCoast, userId)
//        logger.info("Update fixed coast=$updatedCoast for user $userId")
//        return updatedCoast
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
//        val userId = principal.name
//        fixedCoastService.deleteById(id, userId)
//        logger.info("Fixed coast with ID=$id deleted for user $userId")
//    }
}