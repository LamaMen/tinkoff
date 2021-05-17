package com.tinkoff.course_work.dao

import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.models.domain.BaseTransaction
import com.tinkoff.course_work.models.domain.FixedTransaction
import com.tinkoff.course_work.models.domain.Transaction
import org.springframework.stereotype.Repository

@Repository("fixed")
class FixedTransactionDAO(
    private val transactions: TransactionsDAO,
    private val days: FixedDAO
) : MoneyTransactionDAO {

    override suspend fun getTransactionById(userId: String, id: Int?, isCoast: Boolean): Transaction {
        val day = days.getDayByTransactionId(id) ?: throw TransactionNotFoundException(id)
        val base = transactions.getTransactionById(userId, id, isCoast)
        return extractFixedTransaction(base, day)
    }

    override suspend fun getAllTransactionsByUser(userId: String): List<Transaction> {
        val base = transactions.getAllTransactionsByUser(userId)
        return connectData(base)
    }

    override suspend fun addTransaction(userId: String, transaction: Transaction): Int {
        transaction as FixedTransaction
        val id = transactions.addTransaction(userId, BaseTransaction(transaction))
        days.saveDay(id to transaction.day)
        return id
    }

    override suspend fun updateTransaction(userId: String, transaction: Transaction) {
        transactions.updateTransaction(userId, BaseTransaction(transaction))
    }

    override suspend fun deleteTransactionById(userId: String, id: Int, isCoast: Boolean) {
        transactions.getTransactionById(userId, id, isCoast)
        days.deleteTransactionById(id)
        transactions.deleteTransactionById(userId, id, isCoast)
    }

    private suspend fun connectData(transactions: List<BaseTransaction>): List<FixedTransaction> {
        val result = mutableListOf<FixedTransaction>()
        for (transaction in transactions) {
            val day = days.getDayByTransactionId(transaction.id)
            if (day != null) {
                result.add(extractFixedTransaction(transaction, day))
            }
        }

        return result
    }

    private fun extractFixedTransaction(transaction: BaseTransaction, day: Int): FixedTransaction {
        return FixedTransaction(
            transaction.id,
            transaction.title,
            transaction.amount,
            day,
            transaction.isCoast,
            transaction.category,
            transaction.currency
        )
    }
}