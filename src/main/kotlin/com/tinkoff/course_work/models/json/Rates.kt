package com.tinkoff.course_work.models.json

data class Rates(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
