package com.tinkoff.course_work.dao


import com.tinkoff.course_work.models.domain.BaseTransaction
import com.tinkoff.course_work.models.domain.MoneyTransaction
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository("ordinary")
class OrdinaryTransactionDAO(
    private val transactions: TransactionsDAO,
    private val dates: OrdinaryDAO
) : MoneyTransactionDAO {

    override suspend fun getTransactionById(userId: String, id: Int?, isCoast: Boolean): MoneyTransaction {
        val base = transactions.getTransactionById(userId, id, isCoast)
        val date = dates.getDateByTransactionId(id)
        return extractMoneyTransaction(base, date)
    }

    override suspend fun getAllTransactionsByUser(userId: String): List<MoneyTransaction> {
        val base = transactions.getAllTransactionsByUser(userId)
        return base.map {
            val date = dates.getDateByTransactionId(it.id)
            return@map extractMoneyTransaction(it, date)
        }
    }

    override suspend fun addTransaction(userId: String, transaction: MoneyTransaction): Int {
        val id = transactions.addTransaction(userId, extractBaseTransaction(transaction))
        dates.saveDate(id to transaction.date)
        return id
    }

    override suspend fun updateTransaction(userId: String, transaction: MoneyTransaction) {
        transactions.updateTransaction(userId, extractBaseTransaction(transaction))
    }

    override suspend fun deleteTransactionById(userId: String, id: Int, isCoast: Boolean) {
        transactions.deleteTransactionById(userId, id, isCoast)
        dates.deleteTransactionById(id)
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

    private fun extractBaseTransaction(transaction: MoneyTransaction): BaseTransaction {
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

