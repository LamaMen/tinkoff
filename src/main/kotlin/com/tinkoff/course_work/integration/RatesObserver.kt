package com.tinkoff.course_work.integration

import com.tinkoff.course_work.exceptions.NoSuchCurrencyException
import com.tinkoff.course_work.models.json.Rates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.time.Duration
import java.time.LocalDateTime

@Component
class RatesObserver(private val webClient: WebClient) {
    @Value("\${api.key}")
    private lateinit var apiKey: String
    private val needDuration = Duration.ofHours(1)

    private var lastUpdateTime: LocalDateTime? = null
    private var rates: Rates? = null

    suspend fun getRate(currency: String): Double {
        if (isNeedUpdate()) {
            updateRates()
            lastUpdateTime = LocalDateTime.now()
        }

        return rates!!.rates[currency] ?: throw NoSuchCurrencyException("No $currency currency")
    }

    suspend fun checkCurrency(currency: String): Boolean {
        if (rates == null) updateRates()
        return rates!!.rates.containsKey(currency)
    }

    private fun isNeedUpdate(): Boolean {
        if (rates == null || lastUpdateTime == null) return true

        val now = LocalDateTime.now()
        val duration = Duration.between(lastUpdateTime, now)
        return duration > needDuration
    }

    private suspend fun updateRates() {
        rates = webClient
            .get()
            .uri("/latest?access_key=$apiKey")
            .retrieve()
            .awaitBody()
    }
}