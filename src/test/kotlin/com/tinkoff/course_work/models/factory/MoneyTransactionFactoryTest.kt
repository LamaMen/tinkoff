package com.tinkoff.course_work.models.factory

import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.json.Coast
import com.tinkoff.course_work.models.json.Income
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class MoneyTransactionFactoryTest(
    @Autowired val factory: MoneyTransactionFactory
) {

    @Test
    fun `build transaction by parent transaction and coast without id`() {
        val coast = Coast(null, "coast", 1, null)
        val parentTransaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), true)

        val transaction = factory.build(coast, parentTransaction)

        assertEquals(coast.title, transaction.title)
        assertEquals(coast.amount, transaction.amount)
        assertEquals(parentTransaction.id, transaction.id)
        assertEquals(parentTransaction.date, transaction.date)
        assertTrue(transaction.isCoast)
    }

    @Test
    fun `build transaction by parent transaction and coast with id`() {
        val coast = Coast(2, "coast", 1, null)
        val parentTransaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), true)

        val transaction = factory.build(coast, parentTransaction)

        assertEquals(coast.title, transaction.title)
        assertEquals(coast.amount, transaction.amount)
        assertEquals(parentTransaction.id, transaction.id)
        assertEquals(parentTransaction.date, transaction.date)
        assertTrue(transaction.isCoast)
    }


    @Test
    fun `build transaction by parent transaction and income without id`() {
        val income = Income(null, "income", 1, null)
        val parentTransaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), false)

        val transaction = factory.build(income, parentTransaction)

        assertEquals(income.title, transaction.title)
        assertEquals(income.amount, transaction.amount)
        assertEquals(parentTransaction.id, transaction.id)
        assertEquals(parentTransaction.date, transaction.date)
        assertFalse(transaction.isCoast)
    }

    @Test
    fun `build transaction by parent transaction and income with id`() {
        val income = Income(2, "income", 1, null)
        val parentTransaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), false)

        val transaction = factory.build(income, parentTransaction)

        assertEquals(income.title, transaction.title)
        assertEquals(income.amount, transaction.amount)
        assertEquals(parentTransaction.id, transaction.id)
        assertEquals(parentTransaction.date, transaction.date)
        assertFalse(transaction.isCoast)
    }

    @Test
    fun `build transaction by coast without id`() {
        val coast = Coast(null, "coast", 1, LocalDateTime.now())
        val transaction = factory.build(coast)

        assertNull(transaction.id)
        assertEquals(coast.title, transaction.title)
        assertEquals(coast.amount, transaction.amount)
        assertEquals(coast.date, transaction.date)
        assertTrue(transaction.isCoast)
    }

    @Test
    fun `build transaction by coast with id`() {
        val coast = Coast(1, "coast", 1, LocalDateTime.now())
        val transaction = factory.build(coast)

        assertNotNull(transaction.id)
        assertEquals(coast.id, transaction.id)
        assertEquals(coast.title, transaction.title)
        assertEquals(coast.amount, transaction.amount)
        assertEquals(coast.date, transaction.date)
        assertTrue(transaction.isCoast)
    }

    @Test
    fun `build transaction by income without id`() {
        val income = Income(null, "income", 1, LocalDateTime.now())
        val transaction = factory.build(income)

        assertNull(transaction.id)
        assertEquals(income.title, transaction.title)
        assertEquals(income.amount, transaction.amount)
        assertEquals(income.date, transaction.date)
        assertFalse(transaction.isCoast)
    }

    @Test
    fun `build transaction by income with id`() {
        val income = Income(1, "income", 1, LocalDateTime.now())
        val transaction = factory.build(income)

        assertNotNull(transaction.id)
        assertEquals(income.id, transaction.id)
        assertEquals(income.title, transaction.title)
        assertEquals(income.amount, transaction.amount)
        assertEquals(income.date, transaction.date)
        assertFalse(transaction.isCoast)
    }

    @Test
    fun `build transaction by null id and coast`() {
        val coast = Coast(1, "coast", 1, LocalDateTime.now())
        val transaction = factory.build(null, coast)

        assertNull(transaction.id)
        assertEquals(coast.title, transaction.title)
        assertEquals(coast.amount, transaction.amount)
        assertEquals(coast.date, transaction.date)
        assertTrue(transaction.isCoast)
    }

    @Test
    fun `build transaction by id and coast`() {
        val coast = Coast(null, "coast", 1, LocalDateTime.now())
        val transaction = factory.build(1, coast)

        assertNotNull(transaction.id)
        assertEquals(1, transaction.id)
        assertEquals(coast.title, transaction.title)
        assertEquals(coast.amount, transaction.amount)
        assertEquals(coast.date, transaction.date)
        assertTrue(transaction.isCoast)
    }

    @Test
    fun `build transaction by null id and income`() {
        val income = Income(1, "income", 1, LocalDateTime.now())
        val transaction = factory.build(null, income)

        assertNull(transaction.id)
        assertEquals(income.title, transaction.title)
        assertEquals(income.amount, transaction.amount)
        assertEquals(income.date, transaction.date)
        assertFalse(transaction.isCoast)
    }

    @Test
    fun `build transaction by id and income`() {
        val income = Income(null, "income", 1, LocalDateTime.now())
        val transaction = factory.build(1, income)

        assertNotNull(transaction.id)
        assertEquals(1, transaction.id)
        assertEquals(income.title, transaction.title)
        assertEquals(income.amount, transaction.amount)
        assertEquals(income.date, transaction.date)
        assertFalse(transaction.isCoast)
    }
}