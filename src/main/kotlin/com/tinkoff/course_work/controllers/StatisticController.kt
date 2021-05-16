package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.StatisticService
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/statistic")
class StatisticController(
    private val service: StatisticService
) {
    private val logger = LoggerFactory.getLogger(StatisticService::class.java)

    @GetMapping("/balance")
    suspend fun getBalance(
        principal: Principal,
        @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date?,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): Map<String, Long> {
        val userId = principal.name
        val balance = service.getBalance(from, to, userId)
        logger.info("Given balance for user $userId")
        return balance
    }

    @GetMapping("/coasts")
    suspend fun getCoastsFromInterval(
        principal: Principal,
        @RequestParam(name = "from") @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): List<Coast> {
        val userId = principal.name
        val coasts = service.getCoastsFromInterval(from, to, userId)
        logger.info("Given ${coasts.size} coasts in interval $from to $to for user $userId")
        return coasts
    }

    @GetMapping("/incomes")
    suspend fun getIncomeFromInterval(
        principal: Principal,
        @RequestParam(name = "from") @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): List<Income> {
        val userId = principal.name
        val incomes = service.getIncomesFromInterval(from, to, userId)
        logger.info("Given ${incomes.size} incomes in interval $from to $to for user $userId")
        return incomes
    }

    @GetMapping("/group_by_categories")
    suspend fun groupByCategories(principal: Principal): Map<String, List<Coast>> {
        val userId = principal.name
        val coastsByCategories = service.groupByCategories(userId)
        logger.info("Given grouped coasts for user $userId")
        return coastsByCategories
    }

    @GetMapping("/rate/{rate_name}")
    suspend fun getRate(@PathVariable(name = "rate_name") rateName: String): Map<String, Double> {
        val rate = service.getRates(rateName)
        return mapOf(rateName to rate)
    }
}