import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DataBaseTest {

    private val db = mockk<DataBase> {
        every { getNames() } returns listOf("1", "2", "3")
        every { getNameById(range(1, 5, fromInclusive = true, toInclusive = false)) } returns "1"
        every { getNameById(not(range(1, 5, fromInclusive = true, toInclusive = false))) } returns null
    }


    @Test
    fun `test of getting all names`() {
        assertEquals(listOf("1", "2", "3"), db.getNames())
    }

    @Test
    fun `test of getting names with correct id`() {
        assertEquals("1", db.getNameById(1))
        assertEquals("1", db.getNameById(2))
        assertEquals("1", db.getNameById(3))
        assertEquals("1", db.getNameById(4))
    }

    @Test
    fun `test of getting names with incorrect id`() {
        assertNull(db.getNameById(0))
        assertNull(db.getNameById(5))
        assertNull(db.getNameById(56))
    }

}