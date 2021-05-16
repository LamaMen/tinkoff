package com.tinkoff.course_work.config

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.JsonService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MainConfig {
    @Value("\${api.url}")
    private lateinit var apiUrl: String

    @Bean
    fun incomeService(
        dao: MoneyTransactionDAO,
        entityFactory: BasicJsonFactory,
        transactionFactory: MoneyTransactionFactory
    ): JsonService<Income> {
        val service = JsonService<Income>(dao, entityFactory, transactionFactory)
        service.isCoast = false
        return service
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.create(apiUrl)
    }
//    fun webClient(@Value("\${api.url}") apiUrl: String): WebClient {

}