package com.tinkoff.course_work.models.factory

import com.tinkoff.course_work.models.domain.Category
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.domain.Transaction
import com.tinkoff.course_work.models.json.BasicJson
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.OrdinaryJson
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MoneyTransactionFactory {
    fun build(json: BasicJson, parentTransaction: Transaction): MoneyTransaction {
        val isCoast = json is Coast
        if (parentTransaction is MoneyTransaction) {
            return MoneyTransaction(
                parentTransaction.id,
                json.title,
                json.amount,
                parentTransaction.date,
                isCoast,
                Category.valueOf(json.category),
                json.currency!!
            )
        } else {
            throw IllegalArgumentException()
        }
    }

    fun build(json: BasicJson) = build(json.id, json)

    fun build(id: Int?, json: BasicJson): MoneyTransaction {
        if (json is OrdinaryJson) {
            return buildOrdinary(id, json)
        } else {
            throw IllegalArgumentException()
        }
    }

    private fun buildOrdinary(id: Int?, json: OrdinaryJson): MoneyTransaction {
        val date = json.date ?: LocalDateTime.now()
        val currency = json.currency ?: "EUR"
        val isCoast = json is Coast
        return MoneyTransaction(
            id,
            json.title,
            json.amount,
            date,
            isCoast,
            Category.valueOf(json.category),
            currency
        )
    }
}