package com.tinkoff.course_work.models

import java.time.LocalDateTime

data class MoneyTransaction(
    val id: Int?,
    val amount: Long,
    val title: String,
    val date: LocalDateTime,
    val isCoast: Boolean
)