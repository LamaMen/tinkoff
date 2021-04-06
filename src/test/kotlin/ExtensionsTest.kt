import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExtensionsTest {

    @Test
    fun `test extension function on even string`() {
        assertTrue("Hi".isEvenLength())
    }

    @Test
    fun `test extension function on not even string`() {
        assertFalse("Hello".isEvenLength())
    }

    @Test
    fun `test extension function in the incorrect case`() {
        val exception = assertThrows<StringEmptyException> { "".isEvenLength() }
        assertEquals("String is empty", exception.message)
    }
}
