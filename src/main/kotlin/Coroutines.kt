import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    val salaryService = SalaryService()

    val employees = listOf(
        Employee("Andrew", 10, 0),
        Employee("Ilia", 1000, 0),
        Employee("Danny", 1, 0),
    )

    for (employee in employees) {
        salaryService.subscribeToSalary(employee)
    }

    repeat(10) {
        for (employee in employees) {
            val currentSalary = employee.salaryChanel.receive()
            employee.balance += currentSalary
            println("Employee ${employee.name} get ${currentSalary}\$, his balance now = ${employee.balance}")
        }
        println()
    }
}