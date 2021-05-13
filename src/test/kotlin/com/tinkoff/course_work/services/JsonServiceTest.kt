package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.MoneyTransactionDAO
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
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
    private val date = LocalDateTime.now()
    private val listCoast = mutableListOf(
        MoneyTransaction(1, "", 1, date, true),
        MoneyTransaction(2, "", 1, date, true),
        MoneyTransaction(3, "", 1, date, true),
        MoneyTransaction(4, "", 1, date, false),
        MoneyTransaction(5, "", 1, date, false),
        MoneyTransaction(6, "", 1, date, false),
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

            coEvery { updateTransaction(any(), any()) } throws TransactionNotFoundException()
            coEvery {
                updateTransaction(
                    match {
                        it.id in 1..3 && it.isCoast
                    },
                    userId
                )
            } returns Unit
            coEvery {
                updateTransaction(
                    match {
                        it.id in 4..6 && !it.isCoast
                    },
                    userId
                )
            } returns Unit


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
        assertThrowTransactionNotFoundException { coastService.getById(8, userId) }
        assertThrowTransactionNotFoundException { coastService.getById(4, userId) }
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
        assertThrowTransactionNotFoundException { incomeService.getById(8, userId) }
        assertThrowTransactionNotFoundException { incomeService.getById(1, userId) }
    }

    @Test
    fun `add new coast with date`() = runBlocking {
        val coast = Coast(null, "test", 1, date)
        val newCoast = coastService.add(coast, userId)

        assertNotNull(newCoast.id)
        assertEquals(coast.title, newCoast.title)
        assertEquals(coast.amount, newCoast.amount)
        assertEquals(coast.date, newCoast.date)
    }

    @Test
    fun `add new coast without date`() = runBlocking {
        val coast = Coast(null, "test", 1, null)
        val newCoast = coastService.add(coast, userId)

        assertNotNull(newCoast.id)
        assertEquals(coast.title, newCoast.title)
        assertEquals(coast.amount, newCoast.amount)
        assertNotNull(newCoast.date)
    }

    @Test
    fun `add new income with date`() = runBlocking {
        val income = Income(null, "test", 1, date)
        val newIncome = incomeService.add(income, userId)

        assertNotNull(newIncome.id)
        assertEquals(income.title, newIncome.title)
        assertEquals(income.amount, newIncome.amount)
        assertEquals(income.date, newIncome.date)
    }

    @Test
    fun `add new income without date`() = runBlocking {
        val income = Income(null, "test", 1, null)
        val newIncome = incomeService.add(income, userId)

        assertNotNull(newIncome.id)
        assertEquals(income.title, newIncome.title)
        assertEquals(income.amount, newIncome.amount)
        assertNotNull(newIncome.date)
    }

    @Test
    fun `update exist coast`() = runBlocking {
        val id = 1
        val coast = Coast(id, "test", 1, date)
        val newCoast = coastService.update(id, coast, userId)

        assertNotNull(newCoast.id)
        assertEquals(coast.title, newCoast.title)
        assertEquals(coast.amount, newCoast.amount)
        assertEquals(coast.date, newCoast.date)
    }

    @Test
    fun `update non-exist coast`() {
        val id = 9
        val coast = Coast(id, "test", 1, date)
        assertThrowTransactionNotFoundException { coastService.update(id, coast, userId) }
    }

    @Test
    fun `update exist income`() = runBlocking {
        val id = 4
        val income = Income(id, "test", 1, date)
        val newIncome = incomeService.update(id, income, userId)

        assertNotNull(newIncome.id)
        assertEquals(income.title, newIncome.title)
        assertEquals(income.amount, newIncome.amount)
        assertEquals(income.date, newIncome.date)
    }

    @Test
    fun `update non-exist income`() {
        val id = 9
        val income = Income(id, "test", 1, date)
        assertThrowTransactionNotFoundException { incomeService.update(id, income, userId) }
    }

    @Test
    fun `delete exist coast`() = runBlocking {
        val id = 1
        coastService.deleteById(id, userId)
        coVerify(exactly = 1) { dao.deleteTransactionById(id, true, userId) }
    }

    @Test
    fun `delete non-exist coast`() {
        val id = 5
        assertThrowTransactionNotFoundException { coastService.deleteById(id, userId) }
    }

    @Test
    fun `delete exist income`() = runBlocking {
        val id = 4
        incomeService.deleteById(id, userId)
        coVerify(exactly = 1) { dao.deleteTransactionById(id, false, userId) }
    }

    @Test
    fun `delete non-exist income`() {
        val id = 1
        assertThrowTransactionNotFoundException { incomeService.deleteById(id, userId) }
    }

    private fun assertThrowTransactionNotFoundException(block: suspend CoroutineScope.() -> Unit) {
        assertThrows(TransactionNotFoundException::class.java) {
            runBlocking {
                block()
            }
        }
    }
}