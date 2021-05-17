package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.fixed.FixedIncome
import com.tinkoff.course_work.services.JsonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/fixed_income")
class FixedIncomeController(private val fixedIncomeService: JsonService<FixedIncome>) {
    private val logger = LoggerFactory.getLogger(FixedIncomeController::class.java)

    @GetMapping
    suspend fun getAllIncomes(
        principal: Principal,
    ): List<FixedIncome> {
        val userId = principal.name
        val incomes = fixedIncomeService.getFromInterval(userId = userId)
        logger.info("Given ${incomes.size} fixed incomes for user $userId")
        return incomes
    }

    @GetMapping("/{id}")
    suspend fun getIncomeById(principal: Principal, @PathVariable id: Int): FixedIncome {
        val userId = principal.name
        val income = fixedIncomeService.getById(id, userId)
        logger.info("Given fixed income with ID=$id for user $userId")
        return income
    }

    @PostMapping
    suspend fun addIncome(principal: Principal, @RequestBody fixedIncome: FixedIncome): FixedIncome {
        val userId = principal.name
        val savedIncome = fixedIncomeService.add(fixedIncome, userId)
        logger.info("Saved fixed income with ID=${savedIncome.id} for user $userId")
        return savedIncome
    }

    @PutMapping("/{id}")
    suspend fun updateIncome(
        principal: Principal,
        @PathVariable id: Int,
        @RequestBody fixedIncome: FixedIncome
    ): FixedIncome {
        val userId = principal.name
        val updatedIncome = fixedIncomeService.update(id, fixedIncome, userId)
        logger.info("Update fixed coast=$updatedIncome for user $userId")
        return updatedIncome
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteIncome(principal: Principal, @PathVariable id: Int) {
        val userId = principal.name
        fixedIncomeService.deleteById(id, userId)
        logger.info("Fixed coast with ID=$id deleted for user $userId")
    }
}