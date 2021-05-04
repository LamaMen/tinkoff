import kotlinx.coroutines.delay

object EmployeeDAO {
    private val employees = listOf(
        Employee(1, "Andrew"),
        Employee(2, "Ilia"),
        Employee(3, "Danny")
    )

    suspend fun getAllEmployees(): List<Employee> {
        delay(100)
        return employees
    }

    suspend fun getEmployeeById(id: Int): Employee {
        delay(50)
        return employees.find { it.id == id } ?: throw NoSuchElementException()
    }
}