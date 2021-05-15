package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.services.JsonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/coast")
class CoastController(private val coastService: JsonService<Coast>) {
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

    @GetMapping("/category")
    suspend fun getByCategory(principal: Principal, @RequestParam(name = "name") category: String): List<Coast> {
        val userId = principal.name
        return coastService.getByCategory(category, userId)
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