package com.tinkoff.course_work.services

import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.Income
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatisticService(
    private val coastService: JsonService<Coast>,
    private val incomeService: JsonService<Income>,
    private val ratesObserver: RatesObserver
) {
    suspend fun getBalance(currency: String?, from: Date?, to: Date?, userId: String): Map<String, Double> {
        var balance =
            incomeService.getFromInterval(userId = userId, from = from, to = to).fold(0.toDouble()) { sum, income ->
                val amount = ratesObserver.convert(income.amount, from = income.currency)
                sum + amount
            }

        balance = coastService.getFromInterval(userId = userId, from = from, to = to).fold(balance) { sum, coast ->
            val amount = ratesObserver.convert(coast.amount, from = coast.currency)
            sum - amount
        }

        val needCurrency = currency ?: ratesObserver.getBase()
        return mapOf(needCurrency to ratesObserver.convert(balance, to = currency))
    }

    suspend fun groupByCategories(userId: String): Map<String, List<Coast>> {
        return coastService.getJsonByCondition(userId) { it.isCoast }
            .groupBy { coast -> coast.category!! }
    }

    suspend fun getRates(rate: String): Double {
        return ratesObserver.getRate(rate)
    }
}