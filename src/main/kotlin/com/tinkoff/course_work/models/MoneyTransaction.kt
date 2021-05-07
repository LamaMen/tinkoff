package com.tinkoff.course_work.models

import java.time.LocalDateTime

data class MoneyTransaction(
    val id: Int?,
    val title: String,
    val amount: Long,
    val date: LocalDateTime,
    val isCoast: Boolean
) {
    companion object {
        operator fun invoke(coast: Coast, parentTransaction: MoneyTransaction): MoneyTransaction {
            return MoneyTransaction(parentTransaction.id, coast.title, coast.amount, parentTransaction.date, true)
        }

        operator fun invoke(coast: Coast): MoneyTransaction {
            return invoke(coast.id, coast)
        }

        operator fun invoke(id: Int?, coast: Coast): MoneyTransaction {
            val date = coast.date ?: LocalDateTime.now()
            return MoneyTransaction(id, coast.title, coast.amount, date, true)
        }

        operator fun invoke(income: Income, parentTransaction: MoneyTransaction): MoneyTransaction {
            return MoneyTransaction(parentTransaction.id, income.title, income.amount, parentTransaction.date, false)
        }

        operator fun invoke(income: Income): MoneyTransaction {
            return invoke(income.id, income)
        }

        operator fun invoke(id: Int?, income: Income): MoneyTransaction {
            val date = income.date ?: LocalDateTime.now()
            return MoneyTransaction(id, income.title, income.amount, date, false)
        }
    }
}