package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.models.MoneyTransaction
import com.tinkoff.course_work.models.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CoastService(private val dao: MoneyTransactionDAO) {
    fun getAllCoasts(user: User): List<Coast> {
        val id = user.id ?: throw NoSuchElementException()
        return dao.getAllTransactionsByUser(id)
            .filter(MoneyTransaction::isCoast)
            .map { Coast(it.id, it.title, it.amount, it.date) }
    }

    fun addCoastNow(coast: Coast, user: User): Coast {
        val userId = user.id ?: throw NoSuchElementException()
        val dateTimeNow = LocalDateTime.now()
        val coastId = dao.addTransaction(MoneyTransaction(null, coast.title, coast.amount, dateTimeNow, true), userId)
        return Coast(coastId, coast.title, coast.amount, dateTimeNow)
    }
}