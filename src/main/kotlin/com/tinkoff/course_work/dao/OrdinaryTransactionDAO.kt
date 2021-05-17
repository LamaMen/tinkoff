package com.tinkoff.course_work.dao


import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.models.domain.BaseTransaction
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.domain.Transaction
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository("ordinary")
class OrdinaryTransactionDAO(
    private val transactions: TransactionsDAO,
    private val dates: OrdinaryDAO
) : MoneyTransactionDAO {

    override suspend fun getTransactionById(userId: String, id: Int?, isCoast: Boolean): Transaction {
        val date = dates.getDateByTransactionId(id) ?: throw TransactionNotFoundException(id)
        val base = transactions.getTransactionById(userId, id, isCoast)
        return extractMoneyTransaction(base, date)
    }

    override suspend fun getAllTransactionsByUser(userId: String): List<Transaction> {
        val base = transactions.getAllTransactionsByUser(userId)
        return connectData(base)
    }

    override suspend fun addTransaction(userId: String, transaction: Transaction): Int {
        transaction as MoneyTransaction
        val id = transactions.addTransaction(userId, BaseTransaction(transaction))
        dates.saveDate(id to transaction.date)
        return id
    }

    override suspend fun updateTransaction(userId: String, transaction: Transaction) {
        if (dates.getDateByTransactionId(transaction.id) == null) throw TransactionNotFoundException(transaction.id)
        transactions.updateTransaction(userId, BaseTransaction(transaction))
    }

    override suspend fun deleteTransactionById(userId: String, id: Int, isCoast: Boolean) {
        transactions.getTransactionById(userId, id, isCoast)
        dates.deleteTransactionById(id)
        transactions.deleteTransactionById(userId, id, isCoast)
    }

    private suspend fun connectData(transactions: List<BaseTransaction>): List<MoneyTransaction> {
        val result = mutableListOf<MoneyTransaction>()
        for (transaction in transactions) {
            val date = dates.getDateByTransactionId(transaction.id)
            if (date != null) {
                result.add(extractMoneyTransaction(transaction, date))
            }
        }

        return result
    }

    private fun extractMoneyTransaction(transaction: BaseTransaction, date: LocalDateTime): MoneyTransaction {
        return MoneyTransaction(
            transaction.id,
            transaction.title,
            transaction.amount,
            date,
            transaction.isCoast,
            transaction.category,
            transaction.currency
        )
    }
}

