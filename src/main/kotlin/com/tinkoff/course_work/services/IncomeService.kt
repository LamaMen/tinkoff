package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.models.Income
import com.tinkoff.course_work.models.MoneyTransaction
import org.springframework.stereotype.Service

@Service
class IncomeService(private val dao: MoneyTransactionDAO) {
    suspend fun getAllIncomes(userId: String): List<Income> {
        return dao.getAllTransactionsByUser(userId)
            .filter { !it.isCoast }
            .map(Income::invoke)
    }

    suspend fun getById(id: Int, userId: String): Income {
        return Income(dao.getTransactionById(id, false, userId))
    }

    suspend fun addIncomeNow(income: Income, userId: String): Income {
        val transaction = MoneyTransaction(income)
        val incomeId = dao.addTransaction(transaction, userId)
        return Income(incomeId, transaction)
    }

    suspend fun updateIncome(id: Int, income: Income, userId: String): Income {
        val transactionFromDB = dao.getTransactionById(id, false, userId)
        val savedTransaction = MoneyTransaction(income, transactionFromDB)
        dao.updateTransaction(savedTransaction, userId)
        return Income(savedTransaction)
    }

    suspend fun deleteIncomeById(id: Int, userId: String) {
        dao.deleteTransactionById(id, false, userId)
    }
}