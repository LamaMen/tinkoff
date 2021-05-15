package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.StatisticService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/statistic")
class StatisticController(
    private val service: StatisticService
) {
    @GetMapping("/coasts")
    suspend fun getCoastsFromInterval(
        principal: Principal,
        @RequestParam(name = "from") @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): List<Coast> {
        val userId = principal.name
        return service.getCoastsFromInterval(from, to, userId)
    }

    @GetMapping("/incomes")
    suspend fun getIncomeFromInterval(
        principal: Principal,
        @RequestParam(name = "from") @DateTimeFormat(pattern = "d-MMMM-yyyy") from: Date,
        @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "d-MMMM-yyyy") to: Date?
    ): List<Income> {
        val userId = principal.name
        return service.getIncomesFromInterval(from, to, userId)
    }
}