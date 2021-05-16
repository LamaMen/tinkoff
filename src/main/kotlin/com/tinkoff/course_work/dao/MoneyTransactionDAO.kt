package com.tinkoff.course_work.dao

import com.tinkoff.course_work.models.domain.MoneyTransaction

interface MoneyTransactionDAO {

    suspend fun getTransactionById(userId: String, id: Int?, isCoast: Boolean): MoneyTransaction

    suspend fun getAllTransactionsByUser(userId: String): List<MoneyTransaction>

    suspend fun addTransaction(userId: String, transaction: MoneyTransaction): Int

    suspend fun updateTransaction(userId: String, transaction: MoneyTransaction)

    suspend fun deleteTransactionById(userId: String, id: Int, isCoast: Boolean)
}