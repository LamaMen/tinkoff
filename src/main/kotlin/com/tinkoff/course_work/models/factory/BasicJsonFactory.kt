package com.tinkoff.course_work.models.factory

import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.json.BasicJson
import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import org.springframework.stereotype.Component

@Component
class BasicJsonFactory {
    fun <T : BasicJson> build(transaction: MoneyTransaction): T {
        return build(transaction.id, transaction)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BasicJson> build(id: Int?, transaction: MoneyTransaction): T {
        return if (transaction.isCoast) {
            Coast(id, transaction.title, transaction.amount, transaction.date, transaction.category) as T
        } else {
            Income(id, transaction.title, transaction.amount, transaction.date) as T
        }
    }
}