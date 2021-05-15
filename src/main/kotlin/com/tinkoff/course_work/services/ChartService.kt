package com.tinkoff.course_work.services

import com.tinkoff.course_work.integration.URLBuilder
import org.springframework.stereotype.Service

@Service
class ChartService(
    private val statistic: StatisticService
) {
    suspend fun getChartForGroupingCoastsByCategoriesByCount(userId: String): String {
        val categories = statistic.groupByCategories(userId)
        val names = mutableListOf<String>()
        val counts = categories.entries.map { entry ->
            names.add(entry.key)
            return@map entry.value.size
        }

        return buildUrlForPieChart("Coast by categories and counts", counts, names)
    }

    private fun buildUrlForPieChart(title: String, values: List<Int>, names: List<String>): String {
        return URLBuilder()
            .addValues(values)
            .addNames(names)
            .setPieChartType()
            .setTitle(title)
            .setSize(500, 500)
            .build()
    }
}
