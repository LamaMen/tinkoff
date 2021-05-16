package com.tinkoff.course_work.models.json.fixed

import com.tinkoff.course_work.models.domain.isCategory

class FixedCoast(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    override val day: Int,
    override val category: String?,
    override val currency: String?
) : FixedJson {
    override fun validateCategory(): Boolean = this.category == null || this.category.isCategory()
}