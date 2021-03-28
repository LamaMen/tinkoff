import kotlin.concurrent.thread

fun main() {
    createThreads()
}

fun createThreads() {
    FromThread().start()

    Thread(FromRunnable()).start()

    thread { println("Это поток созданный через dsl") }

    val daemonThread = DaemonThread()
    daemonThread.isDaemon = true
    daemonThread.start()

    val lowPriorityThread = LowPriorityThread()
    lowPriorityThread.priority = Thread.MIN_PRIORITY
    lowPriorityThread.start()

    val highPriorityThread = HighPriorityThread()
    highPriorityThread.priority = Thread.MAX_PRIORITY
    highPriorityThread.start()
}