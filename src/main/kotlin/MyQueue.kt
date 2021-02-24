fun <T> queueOf(vararg elements: T): Queue<T> = elements.asQueue()

fun <T> Array<out T>.asQueue(): Queue<T> {
    val queue = MyQueue<T>()
    forEach { queue.enqueue(it) }
    return queue
}

class MyQueue<T> : Queue<T> {
    private val queue = mutableListOf<T>()

    override fun enqueue(element: T) {
        queue.add(element)
    }

    override fun dequeue(): T = queue.removeFirst()
}