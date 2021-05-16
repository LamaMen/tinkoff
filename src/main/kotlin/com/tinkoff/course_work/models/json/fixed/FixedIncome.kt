package com.tinkoff.course_work.models.json.fixed

import com.fasterxml.jackson.annotation.JsonIgnore

class FixedIncome(
    override val id: Int?,
    override val title: String,
    override val amount: Long,
    override val day: Int,
    override val currency: String?
) : FixedJson {
    @JsonIgnore
    override val category: String? = null

    override fun validateCategory(): Boolean = true
}