package com.tinkoff.course_work.models.domain

import com.tinkoff.course_work.exceptions.BadRequestException

enum class Category {
    Other,
    Home,
    Car,
    Health,
    Personal,
    Clothing,
    Food,
    Presents,
    Family,
    Technics;

    companion object {
        private val VALUES = values()
        operator fun invoke(ordinal: Int) = VALUES.firstOrNull { it.ordinal == ordinal } ?: Other

        fun valueOf(name: String?) = try {
            if (name == null) Other else Category.valueOf(name)
        } catch (e: IllegalArgumentException) {
            Other
        }
    }
}

fun MoneyTransaction.categoryId() = this.category.ordinal

fun MoneyTransaction.categoryName() = this.category.name

fun String?.isCategory() = this != null && this in Category.values().map(Category::name)

fun String?.validate() {
    if (!this.isCategory()) {
        throw BadRequestException("No such category")
    }
}