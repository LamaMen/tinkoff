package com.tinkoff.course_work.models.json.ordinary

import com.fasterxml.jackson.annotation.JsonFormat
import com.tinkoff.course_work.models.domain.isCategory
import java.time.LocalDateTime

class Coast(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy HH:mm")
    override val date: LocalDateTime?,
    override val category: String?,
    override val currency: String?
) : OrdinaryJson {
    override fun validateCategory(): Boolean = this.category == null || this.category.isCategory()
}