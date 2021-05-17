package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.services.StatisticService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/statistic")
class StatisticController(
    private val service: StatisticService
) {
    private val logger = LoggerFactory.getLogger(StatisticService::class.java)

    @GetMapping
    suspend fun full(
        principal: Principal,
        @RequestParam(name = "currency", required = false) currency: String?,
    ): Map<String, Any> {
        val userId = principal.name
        val full = service.getFull(userId, currency)
        logger.info("Given full statistic for user $userId")
        return full
    }

    @GetMapping("/fixed_balance")
    suspend fun getFixedBalance(
        principal: Principal,
        @RequestParam(name = "currency", required = false) currency: String?,
    ): Map<String, Map<String, Double>> {
        val userId = principal.name
        val balance = service.getFixedBalance(userId, currency)
        logger.info("Given fixed balance for user $userId")
        return mapOf("Фактический бюджет" to balance)
    }

    @GetMapping("/planed_day_balance")
    suspend fun getPlanedDayBalance(
        principal: Principal,
        @RequestParam(name = "currency", required = false) currency: String?,
    ): Map<String, Map<String, Double>> {
        val userId = principal.name
        val balance = service.getPlanedDayBalance(userId, currency)
        logger.info("Given planed day balance for user $userId")
        return mapOf("Планируемый бюджет на день" to balance)
    }

    @GetMapping("/remainder")
    suspend fun getRemainder(
        principal: Principal,
        @RequestParam(name = "currency", required = false) currency: String?,
    ): Map<String, Map<String, Double>> {
        val userId = principal.name
        val balance = service.getRemainder(userId, currency)
        logger.info("Given remainder for user $userId")
        return mapOf("Остаток до конца месяца" to balance)
    }

    @GetMapping("/day_balance")
    suspend fun getDayBalance(
        principal: Principal,
        @RequestParam(name = "currency", required = false) currency: String?,
    ): Map<String, Map<String, Double>> {
        val userId = principal.name
        val balance = service.getDayBalance(userId, currency)
        logger.info("Given day balance for user $userId")
        return mapOf("Фактический бюджет на день" to balance)
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