package com.tinkoff.course_work.models.factory

import com.tinkoff.course_work.models.domain.Category
import com.tinkoff.course_work.models.domain.FixedTransaction
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.domain.Transaction
import com.tinkoff.course_work.models.json.BasicJson
import com.tinkoff.course_work.models.json.fixed.FixedCoast
import com.tinkoff.course_work.models.json.fixed.FixedJson
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.OrdinaryJson
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MoneyTransactionFactory {
    fun build(json: BasicJson, parentTransaction: Transaction): Transaction {
        val isCoast = json is Coast
        return when (parentTransaction) {
            is MoneyTransaction -> {
                MoneyTransaction(
                    parentTransaction.id,
                    json.title,
                    json.amount,
                    parentTransaction.date,
                    isCoast,
                    Category.valueOf(json.category),
                    json.currency!!
                )
            }
            is FixedTransaction -> {
                FixedTransaction(
                    parentTransaction.id,
                    json.title,
                    json.amount,
                    parentTransaction.day,
                    isCoast,
                    Category.valueOf(json.category),
                    json.currency!!
                )
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    fun build(json: BasicJson) = build(json.id, json)

    fun build(id: Int?, json: BasicJson): Transaction {
        return when (json) {
            is OrdinaryJson -> {
                buildOrdinary(id, json)
            }
            is FixedJson -> {
                buildFixed(id, json)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    private fun buildFixed(id: Int?, json: FixedJson): Transaction {
        val currency = json.currency ?: "EUR"
        val isCoast = json is FixedCoast
        return FixedTransaction(
            id,
            json.title,
            json.amount,
            json.day,
            isCoast,
            Category.valueOf(json.category),
            currency
        )
    }

    private fun buildOrdinary(id: Int?, json: OrdinaryJson): Transaction {
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