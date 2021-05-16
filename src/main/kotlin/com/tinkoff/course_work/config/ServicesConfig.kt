package com.tinkoff.course_work.config

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.Income
import com.tinkoff.course_work.services.JsonService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServicesConfig(
    private val entityFactory: BasicJsonFactory,
    private val transactionFactory: MoneyTransactionFactory,
    private val ratesObserver: RatesObserver
) {
    @Bean
    fun coastService(@Qualifier("ordinary") dao: MoneyTransactionDAO): JsonService<Coast> {
        return JsonService(dao, entityFactory, transactionFactory, ratesObserver)
    }

//    @Bean
//    fun fixedCoastService(): JsonService<FixedCoast> {
//        val service = JsonService<FixedCoast>(dao, entityFactory, transactionFactory, ratesObserver)
//        service.isFixed = true
//        return service
//    }

    @Bean
    fun incomeService(@Qualifier("ordinary") dao: MoneyTransactionDAO): JsonService<Income> {
        val service = JsonService<Income>(dao, entityFactory, transactionFactory, ratesObserver)
        service.isCoast = false
        return service
    }

//    @Bean
//    fun fixedIncomeService(): JsonService<FixedIncome> {
//        val service = JsonService<FixedIncome>(dao, entityFactory, transactionFactory, ratesObserver)
//        service.isCoast = false
//        service.isFixed = true
//        return service
//    }
}