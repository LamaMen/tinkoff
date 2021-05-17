package com.tinkoff.course_work.services

import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.models.json.BasicJson
import com.tinkoff.course_work.models.json.fixed.FixedCoast
import com.tinkoff.course_work.models.json.fixed.FixedIncome
import com.tinkoff.course_work.models.json.fixed.FixedJson
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.Income
import com.tinkoff.course_work.models.json.ordinary.OrdinaryJson
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.YearMonth
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.pow

@Service
class StatisticService(
    private val coastService: JsonService<Coast>,
    private val incomeService: JsonService<Income>,
    private val fixedIncomeService: JsonService<FixedIncome>,
    private val fixedCoastService: JsonService<FixedCoast>,
    private val ratesObserver: RatesObserver
) {
    suspend fun getFull(userId: String, currency: String?): Map<String, Any> {
        val data = getData(userId)
        val date = LocalDateTime.now()
        val remainder = calculateRemainder(data)
        val amountFixedIncome = calculateBalance(data["fixedIncomes"]!!, 0.0)
        val amountFixedCoast = calculateBalance(data["fixedCoasts"]!!, 0.0)

        val statistic = mutableMapOf<String, Any>()
        statistic["День"] = date.dayOfMonth
        statistic["Месяц"] = date.month
        statistic["Год"] = date.year
        statistic["Сумма фикисрованных дохдов в месяц"] = round(amountFixedIncome)
        statistic["Сумма фикисрованных расходов в месяц"] = round(abs(amountFixedCoast))
        statistic["Сумма переменных дохдов до сегодняшнего дня"] = round(calculateBalance(data["incomes"]!!, 0.0))
        statistic["Сумма переменных расходов до сегодняшнего дня"] = round(abs(calculateBalance(data["coasts"]!!, 0.0)))
        statistic["Остаток"] = remainder
        statistic["Планируемый бюджет на день"] = calculateDayBalance(amountFixedCoast + amountFixedIncome, 0)
        statistic["Фактический бюдежт на день до конца месяца"] = calculateDayBalance(remainder, date.dayOfMonth)
        statistic["Валюта"] = getPlanedDayBalance(userId, currency).entries.first().key

        return statistic
    }

    suspend fun groupByCategories(userId: String): Map<String, List<Coast>> {
        return coastService.getJsonByCondition(userId) { it.isCoast }
            .groupBy { coast -> coast.category!! }
    }

    suspend fun getFixedBalance(userId: String, currency: String?): Map<String, Double> {
        val coasts = getFixed(userId, calculateDays(), fixedCoastService)
        val incomes = getFixed(userId, calculateDays(), fixedIncomeService)

        val needCurrency = currency ?: ratesObserver.getBase()
        val balance = calculateBalance(coasts, 0.0) + calculateBalance(incomes, 0.0)
        val value = round(ratesObserver.convert(balance, to = currency))
        return mapOf(needCurrency to value)
    }

    suspend fun getPlanedDayBalance(userId: String, currency: String?): Map<String, Double> {
        val monthBalance = getFixedBalance(userId, currency).entries.first()
        return mapOf(monthBalance.key to calculateDayBalance(monthBalance.value, 0))
    }

    suspend fun getDayBalance(userId: String, currency: String?): Map<String, Double> {
        val monthBalance = getRemainder(userId, currency).entries.first()
        return mapOf(monthBalance.key to calculateDayBalance(monthBalance.value, LocalDateTime.now().dayOfMonth))
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getRemainder(userId: String, currency: String?): Map<String, Double> {
        val data = getData(userId)

        val balance = calculateRemainder(data)

        val needCurrency = currency ?: ratesObserver.getBase()
        val value = round(ratesObserver.convert(balance, to = currency))
        return mapOf(needCurrency to value)
    }

    private suspend fun calculateRemainder(data: Map<String, List<BasicJson>>): Double {
        var balance = 0.0
        for (entry in data) {
            balance = calculateBalance(entry.value, balance)
        }
        return round(balance)
    }

    private fun calculateDayBalance(monthBalance: Double, daysPassed: Int): Double {
        val countOfDays = calculateDays() - daysPassed
        val dayBalance = monthBalance / countOfDays
        return round(dayBalance)
    }

    suspend fun getRates(rate: String): Double {
        return ratesObserver.getRate(rate)
    }

    private suspend fun <T : OrdinaryJson> getOrdinary(
        userId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        service: JsonService<T>
    ): List<T> {
        val begin = Timestamp.valueOf(from)
        val end = Timestamp.valueOf(to)
        return service.getFromInterval(userId = userId, from = begin, to = end)
    }

    private suspend fun <T : FixedJson> getFixed(
        userId: String,
        currentDay: Int,
        service: JsonService<T>
    ): List<T> {
        val jsonsBefore = mutableListOf<T>()
        service.getFromInterval(userId = userId).forEach {
            if (it.day <= currentDay) {
                jsonsBefore.add(it)
            }
        }

        return jsonsBefore
    }

    private suspend fun calculateBalance(list: List<BasicJson>, start: Double): Double {
        return list.fold(start) { sum, json ->
            val amount = ratesObserver.convert(json.amount, from = json.currency)
            val sign = if (json is FixedCoast || json is Coast) -1 else 1
            sum + sign * amount
        }
    }

    private fun calculateDays(): Int {
        val date = LocalDateTime.now()
        val monthYear = YearMonth.of(date.year, date.monthValue)
        return monthYear.lengthOfMonth()
    }

    private fun round(digit: Double): Double {
        val scale = 10.0.pow(2)
        return ceil(digit * scale) / scale
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun getData(userId: String): Map<String, List<BasicJson>> {
        val data = mutableMapOf<String, List<BasicJson>>()

        val currentDate = LocalDateTime.now()
        val beginMonth = LocalDateTime.of(currentDate.year, currentDate.month, 1, 0, 0, 0)

        data["coasts"] = getOrdinary(userId, beginMonth, currentDate, coastService)
        data["incomes"] = getOrdinary(userId, beginMonth, currentDate, incomeService)
        data["fixedIncomes"] = getFixed(userId, currentDate.dayOfMonth, fixedIncomeService)
        data["fixedCoasts"] = getFixed(userId, currentDate.dayOfMonth, fixedCoastService)

        return data
    }
}