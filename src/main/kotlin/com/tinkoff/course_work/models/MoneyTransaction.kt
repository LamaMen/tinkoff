package com.tinkoff.course_work.models

import java.time.LocalDateTime

data class MoneyTransaction(
    val id: Int?,
    val title: String,
    val amount: Long,
    val date: LocalDateTime,
    val isCoast: Boolean
)