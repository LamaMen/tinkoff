package com.tinkoff.course_work.models.json

import java.time.LocalDateTime

abstract class BasicJson(
    val id: Int?,
    val title: String,
    val amount: Long,
    val date: LocalDateTime?
)