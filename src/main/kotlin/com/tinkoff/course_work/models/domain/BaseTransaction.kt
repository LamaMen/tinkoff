package com.tinkoff.course_work.models.domain

data class BaseTransaction(
    val id: Int?,
    val title: String,
    val amount: Long,
    val isCoast: Boolean,
    val category: Category,
    val currency: String
)