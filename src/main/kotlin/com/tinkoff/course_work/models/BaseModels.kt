package com.tinkoff.course_work.models

import java.time.LocalDateTime
import java.util.*

data class Coast(
    val id: Int?,
    val title: String,
    val amount: Long,
    val date: LocalDateTime
)

data class Income(
    val id: Int?,
    val title: String,
    val amount: Long,
    val date: Date
)