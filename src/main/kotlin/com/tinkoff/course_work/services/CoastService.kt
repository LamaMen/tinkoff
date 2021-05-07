package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.models.MoneyTransaction
import org.springframework.stereotype.Service

@Service
class CoastService(private val dao: MoneyTransactionDAO) {
    suspend fun getAllCoasts(userId: String): List<Coast> {
        return dao.getAllTransactionsByUser(userId)
            .filter(MoneyTransaction::isCoast)
            .map(Coast::invoke)
    }

    suspend fun getById(id: Int, userId: String): Coast {
        return Coast(dao.getTransactionById(id, true, userId))
    }

    suspend fun addCoastNow(coast: Coast, userId: String): Coast {
        val transaction = MoneyTransaction(coast)
        val coastId = dao.addTransaction(transaction, userId)
        return Coast(coastId, transaction)
    }

    suspend fun updateCoast(id: Int, coast: Coast, userId: String): Coast {
        val transactionFromDB = dao.getTransactionById(id, true, userId)
        val savedTransaction = MoneyTransaction(coast, transactionFromDB)
        dao.updateTransaction(savedTransaction, userId)
        return Coast(savedTransaction)
    }

    suspend fun deleteCoastById(id: Int, userId: String) {
        dao.deleteTransactionById(id, true, userId)
    }
}