package com.tinkoff.course_work.services

import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatisticService(
    private val coastService: JsonService<Coast>,
    private val incomeService: JsonService<Income>
) {
    suspend fun getCoastsFromInterval(from: Date, to: Date?, userId: String) =
        coastService.getFromInterval(from, to, userId)

    suspend fun getIncomesFromInterval(from: Date, to: Date?, userId: String) =
        incomeService.getFromInterval(from, to, userId)
}