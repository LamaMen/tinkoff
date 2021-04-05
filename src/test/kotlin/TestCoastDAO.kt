import dao.CoastDAO
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import models.Coast
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)
class TestCoastDAO {
    private val testCoasts = mutableListOf(
        Coast(1, "Car", 10000),
        Coast(2, "Phone", 1200),
        Coast(3, "Notebook", 1500),
        Coast(4, "Home", 15000),
        Coast(5, "Wear", 100),
    )

    @Test
    fun `test getting list of coasts`() {
        val coastDAO = spyk(CoastDAO(), recordPrivateCalls = true)

        every { coastDAO getProperty "coasts" } returns testCoasts

        val coasts: List<Coast> = coastDAO.getAllCoasts()

        assertFalse(coasts.isEmpty())
        assertTrue(coasts.contains(Coast(1, "Car", 10000)))
        assertFalse(coasts.contains(Coast(10, "Beer", 10)))
    }
}