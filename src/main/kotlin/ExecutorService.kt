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
    val atomic = AtomicInteger()


    for (i in 1..countThreads) {
        executor.execute(PoolThread(atomic))
    }

    executor.shutdown()
    executor.awaitTermination(1000, TimeUnit.MINUTES)

    return System.currentTimeMillis() - startTime
}

class PoolThread(private val atomic: AtomicInteger) : Runnable {
    override fun run() {
        while (true) {
            synchronized(atomic) {
                if (atomic.get() < 1_000_000) {
                    atomic.getAndIncrement()
                } else {
                    return
                }
            }
        }
    }
}