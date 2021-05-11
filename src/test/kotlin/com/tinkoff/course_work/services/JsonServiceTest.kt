package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonServiceTest {
    private lateinit var dao: MoneyTransactionDAO
    private lateinit var factory: BasicJsonFactory
    private lateinit var transactionFactory: MoneyTransactionFactory
    private lateinit var coastService: JsonService<Coast>
    private lateinit var incomeService: JsonService<Income>

    private val userId = "1"
    private val listCoast = mutableListOf(
        MoneyTransaction(1, "", 1, LocalDateTime.MIN, true),
        MoneyTransaction(2, "", 1, LocalDateTime.MIN, true),
        MoneyTransaction(3, "", 1, LocalDateTime.MIN, true),
        MoneyTransaction(4, "", 1, LocalDateTime.MIN, false),
        MoneyTransaction(5, "", 1, LocalDateTime.MIN, false),
        MoneyTransaction(6, "", 1, LocalDateTime.MIN, false),
    )

    @BeforeAll
    internal fun beforeAll() {
        factory = BasicJsonFactory()
        transactionFactory = MoneyTransactionFactory()

        dao = mockk {
            coEvery { getAllTransactionsByUser(any()) } returns listOf()
            coEvery { getAllTransactionsByUser(userId) } returns listCoast

            coEvery { getTransactionById(any(), any(), any()) } throws TransactionNotFoundException()

            coEvery {
                getTransactionById(
                    range(1, 3, fromInclusive = true, toInclusive = true),
                    true,
                    userId
                )
            } returns listCoast[0]

            coEvery {
                getTransactionById(
                    range(4, 6, fromInclusive = true, toInclusive = true),
                    false,
                    userId
                )
            } returns listCoast[3]

            coEvery { addTransaction(any(), userId) } returns 7
            coEvery { updateTransaction(any(), userId) } returns 6

            coEvery { deleteTransactionById(any(), any(), any()) } throws TransactionNotFoundException()
            coEvery {
                deleteTransactionById(
                    range(1, 3, fromInclusive = true, toInclusive = true),
                    true,
                    userId
                )
            } returns Unit
            coEvery {
                deleteTransactionById(
                    range(4, 6, fromInclusive = true, toInclusive = true),
                    false,
                    userId
                )
            } returns Unit
        }

        coastService = JsonService(dao, factory, transactionFactory)
        incomeService = JsonService(dao, factory, transactionFactory)
        incomeService.isCoast = false
    }

    @Test
    fun `get all coasts`() = runBlocking {
        val coastFromDao = factory.build<Coast>(listCoast[0])
        val coasts = coastService.getAll(userId)

        assertFalse(coasts.isEmpty())
        assertEquals(3, coasts.size)
        assertEquals(coastFromDao.id, coasts[0].id)
        assertEquals(coastFromDao.title, coasts[0].title)
        assertEquals(coastFromDao.amount, coasts[0].amount)
        assertEquals(coastFromDao.date, coasts[0].date)
    }

    @Test
    fun `get coast by id`() = runBlocking {
        val coastFromDao = factory.build<Coast>(listCoast[0])
        val coast = coastService.getById(1, userId)

        assertEquals(coastFromDao.id, coast.id)
        assertEquals(coastFromDao.title, coast.title)
        assertEquals(coastFromDao.amount, coast.amount)
        assertEquals(coastFromDao.date, coast.date)
    }

    @Test
    fun `get coast by non-existent id`() {
        assertThrows(TransactionNotFoundException::class.java) { runBlocking { coastService.getById(8, userId) } }
        assertThrows(TransactionNotFoundException::class.java) { runBlocking { coastService.getById(4, userId) } }
    }


    @Test
    fun `get all income`() = runBlocking {
        val incomeFromDao = factory.build<Income>(listCoast[3])
        val incomes = incomeService.getAll(userId)

        assertFalse(incomes.isEmpty())
        assertEquals(3, incomes.size)
        assertEquals(incomeFromDao.id, incomes[0].id)
        assertEquals(incomeFromDao.title, incomes[0].title)
        assertEquals(incomeFromDao.amount, incomes[0].amount)
        assertEquals(incomeFromDao.date, incomes[0].date)
    }

    @Test
    fun `get income by id`() = runBlocking {
        val incomeFromDao = factory.build<Income>(listCoast[3])
        val income = incomeService.getById(4, userId)

        assertEquals(incomeFromDao.id, income.id)
        assertEquals(incomeFromDao.title, income.title)
        assertEquals(incomeFromDao.amount, income.amount)
        assertEquals(incomeFromDao.date, income.date)
    }

    @Test
    fun `get income by non-existent id`() {
        assertThrows(TransactionNotFoundException::class.java) { runBlocking { incomeService.getById(8, userId) } }
        assertThrows(TransactionNotFoundException::class.java) { runBlocking { incomeService.getById(1, userId) } }
    }
}