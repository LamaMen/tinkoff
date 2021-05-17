package com.tinkoff.course_work.dao

import com.tinkoff.course_work.models.domain.Transaction

interface MoneyTransactionDAO {

    suspend fun getTransactionById(userId: String, id: Int?, isCoast: Boolean): Transaction

    suspend fun getAllTransactionsByUser(userId: String): List<Transaction>

    suspend fun addTransaction(userId: String, transaction: Transaction): Int

    suspend fun updateTransaction(userId: String, transaction: Transaction)

    suspend fun deleteTransactionById(userId: String, id: Int, isCoast: Boolean)
}