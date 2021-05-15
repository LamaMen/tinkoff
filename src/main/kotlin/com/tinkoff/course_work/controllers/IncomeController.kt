package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.JsonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/income")
class IncomeController(private val incomeService: JsonService<Income>) {

    @GetMapping
    suspend fun getAllIncomes(principal: Principal): List<Income> {
        val userId = principal.name
        return incomeService.getAll(userId)
    }

    @GetMapping("/{id}")
    suspend fun getEmployeeById(principal: Principal, @PathVariable id: Int): Income {
        val userId = principal.name
        return incomeService.getById(id, userId)
    }

    @PostMapping
    suspend fun addIncome(principal: Principal, @RequestBody income: Income): Income {
        val userId = principal.name
        return incomeService.add(income, userId)
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody income: Income): Income {
        val userId = principal.name
        return incomeService.update(id, income, userId)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
        val userId = principal.name
        incomeService.deleteById(id, userId)
    }
}