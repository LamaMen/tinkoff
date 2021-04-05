import kotlinx.coroutines.channels.ReceiveChannel

data class Employee(val name: String, val salary: Int, var balance: Long) {
    lateinit var salaryChanel: ReceiveChannel<Int>
}
