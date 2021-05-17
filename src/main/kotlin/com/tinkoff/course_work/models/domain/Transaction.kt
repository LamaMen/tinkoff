package com.tinkoff.course_work.models.domain

import java.time.LocalDateTime

interface Transaction {
    val id: Int?
    val title: String
    val amount: Long
    val isCoast: Boolean
    val category: Category
    val currency: String

    fun checkInterval(begin: LocalDateTime, end: LocalDateTime): Boolean
}