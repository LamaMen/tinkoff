package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.JsonService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*


@RestController
@RequestMapping("/income")
class IncomeController(private val incomeService: JsonService<Income>) {
    init {
        incomeService.isCoast = false
    }

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

    @GetMapping("/interval")
    suspend fun getFromInterval(
        principal: Principal,
        @RequestParam(name = "from") @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): List<Income> {
        val userId = principal.name
        return incomeService.getFromInterval(from, to, userId)
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