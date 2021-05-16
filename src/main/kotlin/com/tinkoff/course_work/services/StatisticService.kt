package com.tinkoff.course_work.services

import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatisticService(
    private val coastService: JsonService<Coast>,
    private val incomeService: JsonService<Income>,
    private val ratesObserver: RatesObserver
) {
    suspend fun getBalance(from: Date?, to: Date?, userId: String): Map<String, Long> {
        var balance = incomeService.getFromInterval(userId = userId, from = from, to = to).fold(0L) { sum, income ->
            sum + income.amount
        }

        balance = coastService.getFromInterval(userId = userId, from = from, to = to).fold(balance) { sum, coast ->
            sum - coast.amount
        }

        return mapOf("balance" to balance)
    }

    suspend fun groupByCategories(userId: String): Map<String, List<Coast>> {
        return coastService.getJsonByCondition(userId) { it.isCoast }
            .groupBy { coast -> coast.category!! }
    }

    suspend fun getRates(rate: String): Double {
        return ratesObserver.getRate(rate)
    }
}