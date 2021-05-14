package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.BasicJson
import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(this::class.java)
    var isCoast: Boolean = true

    suspend fun getAll(userId: String): List<T> {
        val jsons: List<T> = dao.getAllTransactionsByUser(userId)
            .filter { it.isCoast == isCoast }
            .map(entityFactory::build)

        logger.info("Given ${jsons.size} ${if (isCoast) "coasts" else "incomes"} for user $userId")
        return jsons
    }

    suspend fun getById(id: Int, userId: String): T {
        val json: T = entityFactory.build(dao.getTransactionById(id, isCoast, userId))
        logger.info("Given ${if (isCoast) "coast" else "income"} with ID=$id for user $userId")
        return json
    }

    suspend fun getFromInterval(from: Date, to: Date?, userId: String): List<T> {
        val begin = from.toLocalDateTime()
        val end = to?.toLocalDateTime() ?: LocalDateTime.now()
        val jsons: List<T> = dao.getAllTransactionsByUser(userId)
            .filter { transaction ->
                transaction.isCoast == isCoast
                        && transaction.date.isAfter(begin)
                        && transaction.date.isBefore(end)
            }
            .map(entityFactory::build)

        logger.info("Given ${if (isCoast) "coasts" else "incomes"} in interval $begin to $end for user $userId")
        return jsons
    }

    suspend fun add(json: T, userId: String): T {
        val transaction = transactionFactory.build(json)
        val id = dao.addTransaction(transaction, userId)
        return entityFactory.build(id, transaction)
    }

    suspend fun update(id: Int, json: T, userId: String): T {
        val transactionFromDB = dao.getTransactionById(id, isCoast, userId)
        val savedTransaction = transactionFactory.build(json, transactionFromDB)
        dao.updateTransaction(savedTransaction, userId)
        return entityFactory.build(savedTransaction)
    }

    suspend fun deleteById(id: Int, userId: String) {
        dao.deleteTransactionById(id, isCoast, userId)
    }
}