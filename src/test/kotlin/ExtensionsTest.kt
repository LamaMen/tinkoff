import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ExtensionsTest {

    @Test
    fun `test extension function in the correct case`() {
        assertAll(
            { assertFalse("Hello".isEvenLength()) },
            { assertTrue("Hi".isEvenLength()) },
            { assertFalse("123".isEvenLength()) },
            { assertTrue("12".isEvenLength()) },
            { assertTrue("privet".isEvenLength()) },
            { assertFalse("bye".isEvenLength()) },
        )
    }

    @Test
    fun `test extension function in the incorrect case`() {
        val exception = assertThrows<StringEmptyException> { "".isEvenLength() }
        assertEquals("String is empty", exception.message)
    }
}
