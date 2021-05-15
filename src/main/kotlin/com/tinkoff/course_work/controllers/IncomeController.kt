package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.JsonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/income")
class IncomeController(private val incomeService: JsonService<Income>) {
    private val logger = LoggerFactory.getLogger(IncomeController::class.java)

    @GetMapping
    suspend fun getAllIncomes(principal: Principal): List<Income> {
        val userId = principal.name
        val incomes = incomeService.getAll(userId)
        logger.info("Given ${incomes.size} incomes for user $userId")
        return incomes
    }

    @GetMapping("/{id}")
    suspend fun getEmployeeById(principal: Principal, @PathVariable id: Int): Income {
        val userId = principal.name
        val income = incomeService.getById(id, userId)
        logger.info("Given income with ID=$id for user $userId")
        return income
    }

    @PostMapping
    suspend fun addIncome(principal: Principal, @RequestBody income: Income): Income {
        val userId = principal.name
        val savedIncome = incomeService.add(income, userId)
        logger.info("Saved income with ID=${savedIncome.id} for user $userId")
        return savedIncome
    }

    @PutMapping("/{id}")
    suspend fun updateCoast(principal: Principal, @PathVariable id: Int, @RequestBody income: Income): Income {
        val userId = principal.name
        val updatedIncome = incomeService.update(id, income, userId)
        logger.info("Update income=$updatedIncome for user $userId")
        return updatedIncome
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteCoast(principal: Principal, @PathVariable id: Int) {
        val userId = principal.name
        incomeService.deleteById(id, userId)
        logger.info("Income with ID=$id deleted for user $userId")
    }
}