package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.services.JsonService
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*


@RestController
@RequestMapping("/coast")
class CoastController(private val coastService: JsonService<Coast>) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    suspend fun getAllCoasts(principal: Principal): List<Coast> {
        val userId = principal.name
        return coastService.getAll(userId)
    }

    @GetMapping("/{id}")
    suspend fun getEmployeeById(principal: Principal, @PathVariable id: Int): Coast {
        val userId = principal.name
        return coastService.getById(id, userId)
    }

    @GetMapping("/interval")
    suspend fun getFromInterval(
        principal: Principal,
        @RequestParam(name = "from") @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): List<Coast> {
        val userId = principal.name
        return coastService.getFromInterval(from, to, userId)
    }

    @PostMapping
    suspend fun addCoast(principal: Principal, @RequestBody coast: Coast): Coast {
        val userId = principal.name
        return coastService.add(coast, userId)
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody coast: Coast): Coast {
        val userId = principal.name
        return coastService.update(id, coast, userId)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
        val userId = principal.name
        coastService.deleteById(id, userId)
    }
}