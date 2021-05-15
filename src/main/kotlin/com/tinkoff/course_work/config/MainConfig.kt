package com.tinkoff.course_work.config

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.Income
import com.tinkoff.course_work.services.JsonService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MainConfig {
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
}