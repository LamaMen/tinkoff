import kotlin.concurrent.thread

class Resource {
    @Volatile
    var value = 0

    fun increment() = ++value
}

fun main() {
    val times = 50
    val resource = Resource()

    thread {
        for (i in 0 until times) {
            println("increment resource: ${resource.increment()}")
        }
    }

    repeat(3) {
        thread {
            for (i in 0 until times) {
                println("read from ${Thread.currentThread().name}: ${resource.value}")
            }
        }
    }
}
