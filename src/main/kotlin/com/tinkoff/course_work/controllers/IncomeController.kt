package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Income
import com.tinkoff.course_work.services.IncomeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/income")
class IncomeController(private val incomeService: IncomeService) {

    @GetMapping
    suspend fun getAllIncomes(principal: Principal): List<Income> {
        val userId = principal.name
        return incomeService.getAllIncomes(userId)
    }

    @GetMapping("/{id}")
    suspend fun getEmployeeById(principal: Principal, @PathVariable id: Int): Income {
        val userId = principal.name
        return incomeService.getById(id, userId)
    }

    @PostMapping
    suspend fun addIncomeNow(principal: Principal, @RequestBody income: Income): Income {
        val userId = principal.name
        return incomeService.addIncomeNow(income, userId)
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody income: Income): Income {
        val userId = principal.name
        return incomeService.updateIncome(id, income, userId)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
        val userId = principal.name
        incomeService.deleteIncomeById(id, userId)
    }
}