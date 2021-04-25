package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.exceptions.NoSuchUserException
import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.models.MoneyTransaction
import com.tinkoff.course_work.models.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CoastService(private val dao: MoneyTransactionDAO) {
    fun getAllCoasts(user: User): List<Coast> {
        val id = getUserId(user)
        return dao.getAllTransactionsByUser(id)
            .filter(MoneyTransaction::isCoast)
            .map(::executeCoast)
    }

    fun getById(id: Int, user: User): Coast {
        val userId = getUserId(user)
        return executeCoast(dao.getTransactionById(id, userId))
    }

    fun addCoastNow(coast: Coast, user: User): Coast {
        val userId = getUserId(user)
        val dateTimeNow = LocalDateTime.now()
        val coastId = dao.addTransaction(MoneyTransaction(null, coast.title, coast.amount, dateTimeNow, true), userId)
        return Coast(coastId, coast.title, coast.amount, dateTimeNow)
    }

    fun deleteCoastById(id: Int, user: User) {
        val userId = getUserId(user)
        dao.deleteTransactionById(id, userId)
    }

    private fun getUserId(user: User) = user.id ?: throw NoSuchUserException()

    private fun executeCoast(transaction: MoneyTransaction): Coast {
        return Coast(transaction.id, transaction.title, transaction.amount, transaction.date)
    }
}