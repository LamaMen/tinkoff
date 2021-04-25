package com.tinkoff.course_work.models

import java.time.LocalDateTime

data class Coast(
    val id: Int?,
    val title: String,
    val amount: Long,
    val date: LocalDateTime?
) {
    companion object {
        operator fun invoke(transaction: MoneyTransaction): Coast {
            return invoke(transaction.id, transaction)
        }

        operator fun invoke(id: Int?, transaction: MoneyTransaction): Coast {
            return Coast(id, transaction.title, transaction.amount, transaction.date)
        }
    }
}