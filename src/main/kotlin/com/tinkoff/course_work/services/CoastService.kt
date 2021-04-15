package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.Coast
import com.tinkoff.course_work.models.MoneyTransaction
import com.tinkoff.course_work.models.User
import org.springframework.stereotype.Service

@Service
class CoastService(private val dao: MoneyTransactionDAO) {
    fun getAllCoasts(user: User): List<Coast> {
        val id = user.id ?: throw NoSuchElementException()
        return dao.getAllTransactionsByUser(id)
            .filter(MoneyTransaction::isCoast)
            .map { Coast(it.id, it.title, it.amount, it.date) }
    }
}