package com.tinkoff.course_work.models.json

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class Coast(
    id: Int?,
    title: String,
    amount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy HH:mm")
    date: LocalDateTime?
) : BasicJson(id, title, amount, date)