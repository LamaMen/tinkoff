import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DSLTest {

    @Test
    fun `test financial assistant without method`() {
        val financialAssistant = mockk<FinancialAssistant> {
            every { name } returns "Yours best financial assistant"
            every { incomes } returns mutableListOf(100, 300, 57, 89)
            every { addIncomeAndCalculate(any()) } throws IllegalArgumentException()
        }

        assertEquals("Yours best financial assistant", financialAssistant.name)
        assertEquals(4, financialAssistant.incomes.size)
    }

    @Test
    fun `test financial assistant with method and illegal argument`() {
        val financialAssistant = mockk<FinancialAssistant> {
            every { name } returns "Yours best financial assistant"
            every { incomes } returns mutableListOf(100, 300, 57, 89)
            every { addIncomeAndCalculate(any()) } throws IllegalArgumentException()
        }

        assertThrows<java.lang.IllegalArgumentException> { financialAssistant.addIncomeAndCalculate(-1) }
        assertEquals(4, financialAssistant.incomes.size)
    }

    @Test
    fun `test financial assistant with method and correct argument`() {
        val financialAssistant = mockk<FinancialAssistant> {
            every { name } returns "Yours best financial assistant"
            every { incomes } returns mutableListOf(100, 300, 57, 89)
            every { addIncomeAndCalculate(23) } returns 569
        }

        assertEquals(569, financialAssistant.addIncomeAndCalculate(23))
    }
}