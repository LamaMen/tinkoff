package com.tinkoff.course_work.models.domain

data class BaseTransaction(
    val id: Int?,
    val title: String,
    val amount: Long,
    val isCoast: Boolean,
    val category: Category,
    val currency: String
) {
    companion object {
        operator fun invoke(transaction: Transaction): BaseTransaction {
            return BaseTransaction(
                transaction.id,
                transaction.title,
                transaction.amount,
                transaction.isCoast,
                transaction.category,
                transaction.currency
            )
        }
    }
}