package com.tinkoff.course_work.services

import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.integration.URLBuilder
import com.tinkoff.course_work.models.json.Coast
import org.springframework.stereotype.Service
import kotlin.math.roundToLong

@Service
class ChartService(
    private val statistic: StatisticService,
    private val ratesObserver: RatesObserver
) {
    suspend fun getChartForGroupingCoastsByCategoriesByCount(userId: String): String {
        val categories = statistic.groupByCategories(userId)
        val names = getListKeys(categories)
        val counts = categories.values.map { value -> value.size }

        return buildUrlForPieChart("Coast by categories and counts", counts, names)
    }

    suspend fun getChartForGroupingCoastsByCategoriesByAmount(userId: String): String {
        val categories = statistic.groupByCategories(userId)
        val names = getListKeys(categories)
        val counts = categories.values.map { value ->
            value.fold(0L) { sum, coast ->
                (sum + ratesObserver.convert(
                    coast.amount,
                    from = coast.currency
                )).roundToLong()
            }
        }

        return buildUrlForPieChart("Coast by categories and amounts", counts, names)
    }

    private fun getListKeys(categories: Map<String, List<Coast>>) =
        categories.keys.toList()

    private fun buildUrlForPieChart(title: String, values: List<Number>, names: List<String>): String {
        return URLBuilder()
            .addValues(values)
            .addNames(names)
            .setPieChartType()
            .setTitle(title)
            .setSize(500, 500)
            .build()
    }
}
