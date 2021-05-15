package com.tinkoff.course_work.models.domain

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

        fun check(name: String?) = try {
            name != null && name in VALUES.map(Category::name)
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}

fun MoneyTransaction.categoryId() = this.category.ordinal
fun MoneyTransaction.categoryName() = this.category.name
