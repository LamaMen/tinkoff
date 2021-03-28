const val times = 10
const val countThreads = 3

class ResourceWithCountReaders {
    @Volatile
    var value = 0
    var countRead = 0

    fun increment() = ++value
}

class WriteThread(private val resource: ResourceWithCountReaders) : Runnable {
    override fun run() {
        var i = 0
        while (i < times) {
            synchronized(resource) {
                if (resource.countRead != countThreads) {
                    Thread.yield()
                } else {
                    println("increment resource: ${resource.increment()}")
                    resource.countRead = 0
                    i++
                }
            }
        }
    }
}

class ReadThread(private val resource: ResourceWithCountReaders) : Runnable {
    override fun run() {
        var i = 0
        var lastValue = -1
        while (i < times) {
            synchronized(resource) {
                if (resource.value == lastValue) {
                    Thread.yield()
                } else {
                    println("read from ${Thread.currentThread().name}: ${resource.value}")
                    lastValue = resource.value
                    resource.countRead++
                    i++
                }
            }
        }
    }

}

fun main() {
    val resource = ResourceWithCountReaders()
    Thread(WriteThread(resource)).start()

    repeat(countThreads) {
        Thread(ReadThread(resource)).start()
    }
}