@file:Suppress("UNUSED_VARIABLE")

package com.tinkoff.course_work.models.factory

import com.tinkoff.course_work.models.domain.Category
import com.tinkoff.course_work.models.domain.MoneyTransaction
import com.tinkoff.course_work.models.json.ordinary.Coast
import com.tinkoff.course_work.models.json.ordinary.Income
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class BasicJsonFactoryTest {
    private val factory: BasicJsonFactory = BasicJsonFactory()

    @Test
    fun `build coast by transaction without id`() {
        val transaction = MoneyTransaction(null, "transaction", 10, LocalDateTime.now(), true, Category.Other, "EUR")
        val coast = factory.build<Coast>(transaction)

        assertNull(coast.id)
        assertEquals(transaction.title, coast.title)
        assertEquals(transaction.amount, coast.amount)
        assertEquals(transaction.date, coast.date)
    }

    @Test
    fun `build coast by transaction with id`() {
        val transaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), true, Category.Other, "EUR")
        val coast = factory.build<Coast>(transaction)

        assertNotNull(coast.id)
        assertEquals(transaction.id, coast.id)
        assertEquals(transaction.title, coast.title)
        assertEquals(transaction.amount, coast.amount)
        assertEquals(transaction.date, coast.date)
    }

    @Test
    fun `build income by transaction without id`() {
        val transaction = MoneyTransaction(null, "transaction", 10, LocalDateTime.now(), false, Category.Other, "EUR")
        val income = factory.build<Income>(transaction)

        assertNull(income.id)
        assertEquals(transaction.title, income.title)
        assertEquals(transaction.amount, income.amount)
        assertEquals(transaction.date, income.date)
    }

    @Test
    fun `build income by transaction with id`() {
        val transaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), false, Category.Other, "EUR")
        val income = factory.build<Income>(transaction)

        assertNotNull(income.id)
        assertEquals(transaction.id, income.id)
        assertEquals(transaction.title, income.title)
        assertEquals(transaction.amount, income.amount)
        assertEquals(transaction.date, income.date)
    }

    @Test
    fun `build coast by null id and transaction`() {
        val id = null
        val transaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), true, Category.Other, "EUR")
        val coast = factory.build<Coast>(id, transaction)

        assertNull(coast.id)
        assertEquals(transaction.title, coast.title)
        assertEquals(transaction.amount, coast.amount)
        assertEquals(transaction.date, coast.date)
    }

    @Test
    fun `build coast by id and transaction`() {
        val id = 1
        val transaction = MoneyTransaction(null, "transaction", 10, LocalDateTime.now(), true, Category.Other, "EUR")
        val coast = factory.build<Coast>(id, transaction)

        assertNotNull(coast.id)
        assertEquals(id, coast.id)
        assertEquals(transaction.title, coast.title)
        assertEquals(transaction.amount, coast.amount)
        assertEquals(transaction.date, coast.date)
    }

    @Test
    fun `build income by null id and transaction`() {
        val id = null
        val transaction = MoneyTransaction(1, "transaction", 10, LocalDateTime.now(), false, Category.Other, "EUR")
        val income = factory.build<Income>(id, transaction)

        assertNull(income.id)
        assertEquals(transaction.title, income.title)
        assertEquals(transaction.amount, income.amount)
        assertEquals(transaction.date, income.date)
    }

    @Test
    fun `build income by id and transaction`() {
        val id = 1
        val transaction = MoneyTransaction(null, "transaction", 10, LocalDateTime.now(), false, Category.Other, "EUR")
        val income = factory.build<Income>(id, transaction)

        assertNotNull(income.id)
        assertEquals(id, income.id)
        assertEquals(transaction.title, income.title)
        assertEquals(transaction.amount, income.amount)
        assertEquals(transaction.date, income.date)
    }

    @Test
    fun `build coast by transaction with income`() {
        val transaction = MoneyTransaction(null, "transaction", 10, LocalDateTime.now(), false, Category.Other, "EUR")
        assertThrows(ClassCastException::class.java) { val coast = factory.build<Coast>(transaction) }
    }

    @Test
    fun `build income by transaction with coast`() {
        val transaction = MoneyTransaction(null, "transaction", 10, LocalDateTime.now(), true, Category.Other, "EUR")
        assertThrows(ClassCastException::class.java) { val income = factory.build<Income>(transaction) }
    }
}