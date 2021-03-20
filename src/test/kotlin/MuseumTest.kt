import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MuseumTest {

    private val museum = Museum(listOf("Starlight Night", "Mona Lisa", "Girl with peaches"))

    @Test
    fun `test of checking an existing painting in a museum`() {
        assertTrue(museum.contains("Mona Lisa"))
    }

    @Test
    fun `test for checking a non-existent painting in a museum`() {
        assertFalse(museum.contains("Ninth wave"))
    }

    @Test
    fun `test of getting an existing picture by id`() {
        assertEquals("Starlight Night", museum.getById(0))
    }

    @Test
    fun `test of getting a non-existent picture by id`() {
        assertNull(museum.getById(15))
    }
}