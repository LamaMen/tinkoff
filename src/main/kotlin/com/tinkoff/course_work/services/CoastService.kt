package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.models.MoneyTransaction
import org.springframework.stereotype.Service

@Service
class CoastService(private val dao: MoneyTransactionDAO) {
    suspend fun getAllCoasts(userId: Int): List<Coast> {
        return dao.getAllTransactionsByUser(userId)
            .filter(MoneyTransaction::isCoast)
            .map(::executeCoast)
    }

    suspend fun getById(id: Int, userId: Int): Coast {
        return executeCoast(dao.getTransactionById(id, userId))
    }

    suspend fun addCoastNow(coast: Coast, userId: Int): Coast {
        val transaction = MoneyTransaction(coast)
        val coastId = dao.addTransaction(transaction, userId)
        return Coast(coastId, transaction)
    }

    suspend fun updateCoast(id: Int, coast: Coast, userId: Int): Coast {
        val transactionFromDB = dao.getTransactionById(id, userId)
        val savedTransaction = MoneyTransaction(coast, transactionFromDB)
        dao.updateTransaction(savedTransaction, userId)
        return Coast(savedTransaction)
    }

    suspend fun deleteCoastById(id: Int, userId: Int) {
        dao.deleteTransactionById(id, userId)
    }

    private fun executeCoast(transaction: MoneyTransaction): Coast {
        return Coast(transaction.id, transaction.title, transaction.amount, transaction.date)
    }
}