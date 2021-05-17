package com.tinkoff.course_work.models.domain

import java.time.LocalDateTime

class MoneyTransaction(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    val date: LocalDateTime,
    override val isCoast: Boolean,
    override val category: Category,
    override val currency: String
) : Transaction {
    override fun checkInterval(begin: LocalDateTime, end: LocalDateTime): Boolean {
        return date.isAfter(begin) && date.isBefore(end)
    }
}