package com.tinkoff.course_work.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Income(
    val id: Int?,
    val title: String,
    val amount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm")
    val date: LocalDateTime?
) {
    companion object {
        operator fun invoke(transaction: MoneyTransaction): Income {
            return invoke(transaction.id, transaction)
        }

        operator fun invoke(id: Int?, transaction: MoneyTransaction): Income {
            return Income(id, transaction.title, transaction.amount, transaction.date)
        }
    }
}