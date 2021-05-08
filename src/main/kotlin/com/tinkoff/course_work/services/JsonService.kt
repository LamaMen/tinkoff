package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.BasicJson
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("prototype")
class JsonService<T : BasicJson>(
    private val dao: MoneyTransactionDAO,
    private val entityFactory: BasicJsonFactory,
    private val transactionFactory: MoneyTransactionFactory
) {
    var isCoast: Boolean = true

    suspend fun getAll(userId: String): List<T> {
        return dao.getAllTransactionsByUser(userId)
            .filter { it.isCoast == isCoast }
            .map(entityFactory::build)
    }

    suspend fun getById(id: Int, userId: String): T {
        return entityFactory.build(dao.getTransactionById(id, isCoast, userId))
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