import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlin.random.Random

object SalaryService {
    @ExperimentalCoroutinesApi
    fun subscribeToSalary(employee: EmployeeWithSalary) {
        employee.salaryChanel = GlobalScope.produce {
            while (true) {
                val salaryWithBonus = employee.salary * Random.nextDouble(1.0, 2.0)
                send(salaryWithBonus.toInt())
                delay(1000L)
            }
        }
    }
}
