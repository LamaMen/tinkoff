package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.exceptions.BadRequestException
import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.domain.categoryName
import com.tinkoff.course_work.models.domain.validate
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.BasicJson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.coroutines.*

@Service
@Scope("prototype")
class JsonService<T : BasicJson>(
    private val dao: MoneyTransactionDAO,
    private val entityFactory: BasicJsonFactory,
    private val transactionFactory: MoneyTransactionFactory,
    private val ratesObserver: RatesObserver
) {
    var isCoast: Boolean = true

    suspend fun getFromInterval(userId: String, isAll: Boolean = false, from: Date? = null, to: Date? = null): List<T> {
        val begin = from?.toLocalDateTime() ?: LocalDateTime.MIN
        val end = to?.toLocalDateTime() ?: if (!isAll) LocalDateTime.now() else LocalDateTime.MAX

        return getJsonByCondition(userId) { transaction ->
            transaction.isCoast == isCoast
                    && transaction.date.isAfter(begin)
                    && transaction.date.isBefore(end)
        }
    }

    suspend fun getById(id: Int, userId: String): T {
        return entityFactory.build(dao.getTransactionById(userId, id, isCoast))
    }

    suspend fun getByCategory(category: String, userId: String): List<T> {
        category.validate()

        return getJsonByCondition(userId) { transaction ->
            transaction.isCoast == isCoast
                    && transaction.categoryName() == category
        }
    }

    suspend fun add(json: T, userId: String): T {
        if (!validateJson(json)) throw BadRequestException("Wrong category or currency")

        val transaction = transactionFactory.build(json)
        val id = dao.addTransaction(userId, transaction)
        return entityFactory.build(id, transaction)
    }

    suspend fun update(id: Int, json: T, userId: String): T {
        if (!validateJson(json, true)) throw BadRequestException("Wrong category or currency")

        val transactionFromDB = dao.getTransactionById(userId, id, isCoast)
        val savedTransaction = transactionFactory.build(json, transactionFromDB)
        dao.updateTransaction(userId, savedTransaction)
        return entityFactory.build(savedTransaction)
    }

    suspend fun deleteById(id: Int, userId: String) {
        dao.deleteTransactionById(userId, id, isCoast)
    }

    suspend fun getJsonByCondition(userId: String, condition: (MoneyTransaction) -> Boolean): List<T> =
        dao.getAllTransactionsByUser(userId)
            .filter(condition)
            .map(entityFactory::build)

    private suspend fun validateJson(json: T, isUpdate: Boolean = false): Boolean = coroutineScope {
        val isCurrencyValid = async {
            json.currency?.let { ratesObserver.checkCurrency(it) } ?: !isUpdate
        }
        val isCategoryValid = json.validateCategory()

        return@coroutineScope isCategoryValid && isCurrencyValid.await()
    }
}