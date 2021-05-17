package com.tinkoff.course_work.models.domain

import java.time.LocalDateTime

class FixedTransaction(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    val day: Int,
    override val isCoast: Boolean,
    override val category: Category,
    override val currency: String
) : Transaction {
    override fun checkInterval(begin: LocalDateTime, end: LocalDateTime): Boolean {
        return true
    }
}