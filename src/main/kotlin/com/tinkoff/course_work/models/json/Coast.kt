package com.tinkoff.course_work.models.json

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class Coast(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy HH:mm")
    override val date: LocalDateTime?,
    override val category: String?
) : BasicJson