import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    workWithTwoServices()
    subscribeToEvent()
}

@ExperimentalCoroutinesApi
suspend fun subscribeToEvent() {
    val salaryService = SalaryService()

    val employees = EmployeeService.getAllEmployees()

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

suspend fun workWithTwoServices() {
    val employee = EmployeeService.getEmployeeWithSalaryById(2)
    println("Рабочий под номером 2 это ${employee.name}")
}