package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.exceptions.NoSuchUserException
import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.models.MoneyTransaction
import com.tinkoff.course_work.models.User
import org.springframework.stereotype.Service

@Service
class CoastService(private val dao: MoneyTransactionDAO) {
    suspend fun getAllCoasts(user: User): List<Coast> {
        val id = getUserId(user)
        return dao.getAllTransactionsByUser(id)
            .filter(MoneyTransaction::isCoast)
            .map(::executeCoast)
    }

    suspend fun getById(id: Int, user: User): Coast {
        val userId = getUserId(user)
        return executeCoast(dao.getTransactionById(id, userId))
    }

    suspend fun addCoastNow(coast: Coast, user: User): Coast {
        val userId = getUserId(user)
        val transaction = MoneyTransaction(coast)
        val coastId = dao.addTransaction(transaction, userId)
        return Coast(coastId, transaction)
    }

    suspend fun updateCoast(id: Int, coast: Coast, user: User): Coast {
        val userId = getUserId(user)
        val transactionFromDB = dao.getTransactionById(id, userId)
        val savedTransaction = MoneyTransaction(coast, transactionFromDB)
        dao.updateTransaction(savedTransaction, userId)
        return Coast(savedTransaction)
    }

    suspend fun deleteCoastById(id: Int, user: User) {
        val userId = getUserId(user)
        dao.deleteTransactionById(id, userId)
    }

    private fun getUserId(user: User) = user.id ?: throw NoSuchUserException()

    private fun executeCoast(transaction: MoneyTransaction): Coast {
        return Coast(transaction.id, transaction.title, transaction.amount, transaction.date)
    }
}