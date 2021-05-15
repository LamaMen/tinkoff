package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.services.ChartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/chart")
class ChartController(
    private val service: ChartService
) {
    @GetMapping("/counts")
    suspend fun groupByCategories(principal: Principal): String {
        val userId = principal.name
        return service.getChartForGroupingCoastsByCategoriesByCount(userId)
    }
}