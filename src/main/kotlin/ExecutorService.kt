import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    println("Пул из 10 потоков: ${lunchExecutor(10)} мс")
    println("Пул из 20 потоков: ${lunchExecutor(20)} мс")
    println("Пул из 30 потоков: ${lunchExecutor(30)} мс")
}

fun lunchExecutor(countThreads: Int): Long {
    val executor = Executors.newFixedThreadPool(countThreads)
    val startTime = System.currentTimeMillis()


    for (i in 1..countThreads) {
        executor.execute(PoolThread())
    }

    executor.shutdown()
    executor.awaitTermination(1000, TimeUnit.MINUTES)

    return System.currentTimeMillis() - startTime
}

class PoolThread : Runnable {
    private val atomic = AtomicInteger()

    override fun run() {
        while (atomic.getAndIncrement() < 1_000_000) {}
    }
}