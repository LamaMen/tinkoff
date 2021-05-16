package com.tinkoff.course_work.models.json

import java.time.LocalDateTime

interface BasicJson {
    val id: Int?
    val title: String
    val amount: Long
    val date: LocalDateTime?
    val category: String?
    val currency: String?

    fun validateCategory(): Boolean
}