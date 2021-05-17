package com.tinkoff.course_work.models.factory

import com.tinkoff.course_work.models.domain.FixedTransaction
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.domain.Transaction
import com.tinkoff.course_work.models.domain.categoryName
import com.tinkoff.course_work.models.json.BasicJson
import com.tinkoff.course_work.models.json.fixed.FixedCoast
import com.tinkoff.course_work.models.json.fixed.FixedIncome
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.Income
import org.springframework.stereotype.Component

@Component
class BasicJsonFactory {
    fun <T : BasicJson> build(transaction: Transaction): T {
        return build(transaction.id, transaction)
    }

    fun <T : BasicJson> build(id: Int?, transaction: Transaction): T {
        return if (transaction is MoneyTransaction) {
            if (transaction.isCoast) {
                buildCoast(id, transaction)
            } else {
                buildIncome(id, transaction)
            }
        } else if (transaction is FixedTransaction) {
            if (transaction.isCoast) {
                buildFixedCoast<T>(id, transaction)
            } else {
                buildFixedIncome(id, transaction)
            }
        } else {
            throw IllegalArgumentException()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : BasicJson> buildFixedCoast(id: Int?, transaction: FixedTransaction): T {
        return FixedCoast(
            id,
            transaction.title,
            transaction.amount,
            transaction.day,
            transaction.categoryName(),
            transaction.currency
        ) as T
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : BasicJson> buildFixedIncome(id: Int?, transaction: FixedTransaction): T {
        return FixedIncome(
            id,
            transaction.title,
            transaction.amount,
            transaction.day,
            transaction.currency
        ) as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BasicJson> buildCoast(id: Int?, transaction: MoneyTransaction): T {
        return Coast(
            id,
            transaction.title,
            transaction.amount,
            transaction.date,
            transaction.categoryName(),
            transaction.currency
        ) as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BasicJson> buildIncome(id: Int?, transaction: MoneyTransaction): T {
        return Income(id, transaction.title, transaction.amount, transaction.date, transaction.currency) as T
    }
}