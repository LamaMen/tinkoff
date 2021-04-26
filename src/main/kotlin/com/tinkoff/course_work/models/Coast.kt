package com.tinkoff.course_work.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Coast(
    val id: Int?,
    val title: String,
    val amount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm")
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