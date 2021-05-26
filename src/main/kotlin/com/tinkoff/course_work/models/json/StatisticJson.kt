package com.tinkoff.course_work.models.json

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class StatisticJson(
    @JsonProperty("Дата")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy")
    val date: LocalDate,
    @JsonProperty("Сумма фикисрованных дохдов в месяц")
    val fixedIncomes: Double,
    @JsonProperty("Сумма фикисрованных расходов в месяц")
    val fixedCoasts: Double,
    @JsonProperty("Сумма переменных дохдов до сегодняшнего дня")
    val ordinaryIncomes: Double,
    @JsonProperty("Сумма переменных расходов до сегодняшнего дня")
    val ordinaryCoasts: Double,
    @JsonProperty("Остаток")
    val balance: Double,
    @JsonProperty("Планируемый бюджет на день")
    val planedDayBalance: Double,
    @JsonProperty("Фактический бюдежт на день до конца месяца")
    val dayBalance: Double,
    @JsonProperty("Валюта")
    val currency: String
)