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