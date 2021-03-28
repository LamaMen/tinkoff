import kotlin.concurrent.thread

class FromThread : Thread() {
    override fun run() = println("Это поток созданный через Thread")
}

class FromRunnable : Runnable {
    override fun run() = println("Это поток созданный через Runnable")
}

class DaemonThread : Thread() {
    override fun run() {
        println("Это поток daemon: $isDaemon")
    }
}

class LowPriorityThread : Thread() {
    override fun run() {
        println("Это поток с низким приоритетом: $priority")
    }
}

class HighPriorityThread : Thread() {
    override fun run() {
        println("Это поток с высоким приоритетом: $priority")
    }
}

fun main() {
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