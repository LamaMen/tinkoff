package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.OrdinaryMoneyTransactionDAO
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.integration.RatesObserver
import com.tinkoff.course_work.models.domain.Category
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.factory.BasicJsonFactory
import com.tinkoff.course_work.models.factory.MoneyTransactionFactory
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.Income
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
    private lateinit var dao: OrdinaryMoneyTransactionDAO
    private lateinit var factory: BasicJsonFactory
    private lateinit var transactionFactory: MoneyTransactionFactory
    private lateinit var coastService: JsonService<Coast>
    private lateinit var incomeService: JsonService<Income>

    private val userId = "1"
    private val date = LocalDateTime.now()
    private val listCoast = mutableListOf(
        MoneyTransaction(1, "", 1, date, true, Category.Other, "EUR"),
        MoneyTransaction(2, "", 1, date, true, Category.Other, "EUR"),
        MoneyTransaction(3, "", 1, date, true, Category.Other, "EUR"),
        MoneyTransaction(4, "", 1, date, false, Category.Other, "EUR"),
        MoneyTransaction(5, "", 1, date, false, Category.Other, "EUR"),
        MoneyTransaction(6, "", 1, date, false, Category.Other, "EUR"),
    )

    @BeforeAll
    internal fun beforeAll() {
        factory = BasicJsonFactory()
        transactionFactory = MoneyTransactionFactory()

        dao = mockk {
            coEvery { getAllTransactionsByUser(any()) } returns listOf()
            coEvery { getAllTransactionsByUser(userId) } returns listCoast

            coEvery { getTransactionById(any(), any(), any()) } throws TransactionNotFoundException(0)

            coEvery {
                getTransactionById(
                    userId,
                    range(1, 3, fromInclusive = true, toInclusive = true),
                    true
                )
            } returns listCoast[0]

            coEvery {
                getTransactionById(
                    userId,
                    range(4, 6, fromInclusive = true, toInclusive = true),
                    false
                )
            } returns listCoast[3]

            coEvery { addTransaction(userId, any()) } returns 7

            coEvery { updateTransaction(any(), any()) } throws TransactionNotFoundException(0)
            coEvery {
                updateTransaction(
                    userId,
                    match {
                        it.id in 1..3 && it.isCoast
                    }
                )
            } returns Unit
            coEvery {
                updateTransaction(
                    userId,
                    match {
                        it.id in 4..6 && !it.isCoast
                    }
                )
            } returns Unit


            coEvery { deleteTransactionById(any(), any(), any()) } throws TransactionNotFoundException(0)
            coEvery {
                deleteTransactionById(
                    userId,
                    range(1, 3, fromInclusive = true, toInclusive = true),
                    true
                )
            } returns Unit
            coEvery {
                deleteTransactionById(
                    userId,
                    range(4, 6, fromInclusive = true, toInclusive = true),
                    false
                )
            } returns Unit
        }

        val ratesObserver: RatesObserver = mockk()

        coastService = JsonService(dao, factory, transactionFactory, ratesObserver)
        incomeService = JsonService(dao, factory, transactionFactory, ratesObserver)
        incomeService.isCoast = false
    }

    @Test
    fun `get all coasts`() = runBlocking {
        val coastFromDao = factory.build<Coast>(listCoast[0])
        val coasts = coastService.getFromInterval(userId)

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
        val incomes = incomeService.getFromInterval(userId)

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
        val coast = Coast(null, "test", 1, date, null, "EUR")
        val newCoast = coastService.add(coast, userId)

        assertNotNull(newCoast.id)
        assertEquals(coast.title, newCoast.title)
        assertEquals(coast.amount, newCoast.amount)
        assertEquals(coast.date, newCoast.date)
    }

    @Test
    fun `add new coast without date`() = runBlocking {
        val coast = Coast(null, "test", 1, null, null, "EUR")
        val newCoast = coastService.add(coast, userId)

        assertNotNull(newCoast.id)
        assertEquals(coast.title, newCoast.title)
        assertEquals(coast.amount, newCoast.amount)
        assertNotNull(newCoast.date)
    }

    @Test
    fun `add new income with date`() = runBlocking {
        val income = Income(null, "test", 1, date, "EUR")
        val newIncome = incomeService.add(income, userId)

        assertNotNull(newIncome.id)
        assertEquals(income.title, newIncome.title)
        assertEquals(income.amount, newIncome.amount)
        assertEquals(income.date, newIncome.date)
    }

    @Test
    fun `add new income without date`() = runBlocking {
        val income = Income(null, "test", 1, null, "EUR")
        val newIncome = incomeService.add(income, userId)

        assertNotNull(newIncome.id)
        assertEquals(income.title, newIncome.title)
        assertEquals(income.amount, newIncome.amount)
        assertNotNull(newIncome.date)
    }

    @Test
    fun `update exist coast`() = runBlocking {
        val id = 1
        val coast = Coast(id, "test", 1, date, null, "EUR")
        val newCoast = coastService.update(id, coast, userId)

        assertNotNull(newCoast.id)
        assertEquals(coast.title, newCoast.title)
        assertEquals(coast.amount, newCoast.amount)
        assertEquals(coast.date, newCoast.date)
    }

    @Test
    fun `update non-exist coast`() {
        val id = 9
        val coast = Coast(id, "test", 1, date, null, "EUR")
        assertThrowTransactionNotFoundException { coastService.update(id, coast, userId) }
    }

    @Test
    fun `update exist income`() = runBlocking {
        val id = 4
        val income = Income(id, "test", 1, date, "EUR")
        val newIncome = incomeService.update(id, income, userId)

        assertNotNull(newIncome.id)
        assertEquals(income.title, newIncome.title)
        assertEquals(income.amount, newIncome.amount)
        assertEquals(income.date, newIncome.date)
    }

    @Test
    fun `update non-exist income`() {
        val id = 9
        val income = Income(id, "test", 1, date, "EUR")
        assertThrowTransactionNotFoundException { incomeService.update(id, income, userId) }
    }

    @Test
    fun `delete exist coast`() = runBlocking {
        val id = 1
        coastService.deleteById(id, userId)
        coVerify(exactly = 1) { dao.deleteTransactionById(userId, id, true) }
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
        coVerify(exactly = 1) { dao.deleteTransactionById(userId, id, false) }
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