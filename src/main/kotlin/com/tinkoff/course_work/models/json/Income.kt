package com.tinkoff.course_work.models.json

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

class Income(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy HH:mm")
    override val date: LocalDateTime?
) : BasicJson {
    @JsonIgnore
    override val category: String? = null

    override fun validateCategory() {}
}