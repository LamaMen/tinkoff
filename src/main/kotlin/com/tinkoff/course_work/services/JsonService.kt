package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.domain.categoryName
import com.tinkoff.course_work.models.domain.validate
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.BasicJson
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
@Scope("prototype")
class JsonService<T : BasicJson>(
    private val dao: MoneyTransactionDAO,
    private val entityFactory: BasicJsonFactory,
    private val transactionFactory: MoneyTransactionFactory
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
        return entityFactory.build(dao.getTransactionById(id, isCoast, userId))
    }

    suspend fun getByCategory(category: String, userId: String): List<T> {
        category.validate()

        return getJsonByCondition(userId) { transaction ->
            transaction.isCoast == isCoast
                    && transaction.categoryName() == category
        }
    }

    suspend fun add(json: T, userId: String): T {
        json.validateCategory()
        val transaction = transactionFactory.build(json)
        val id = dao.addTransaction(transaction, userId)
        return entityFactory.build(id, transaction)
    }

    suspend fun update(id: Int, json: T, userId: String): T {
        json.validateCategory()
        val transactionFromDB = dao.getTransactionById(id, isCoast, userId)
        val savedTransaction = transactionFactory.build(json, transactionFromDB)
        dao.updateTransaction(savedTransaction, userId)
        return entityFactory.build(savedTransaction)
    }

    suspend fun deleteById(id: Int, userId: String) {
        dao.deleteTransactionById(id, isCoast, userId)
    }

    suspend fun getJsonByCondition(userId: String, condition: (MoneyTransaction) -> Boolean): List<T> =
        dao.getAllTransactionsByUser(userId)
            .filter(condition)
            .map(entityFactory::build)
}