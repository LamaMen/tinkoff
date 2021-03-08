fun <T> queueOf(vararg elements: T): Queue<T> = elements.asQueue()
fun <T> queueOf(): Queue<T> = MyQueue()

fun <T> Array<out T>.asQueue(): Queue<T> {
    val queue = MyQueue<T>()
    forEach(queue::enqueue)
    return queue
}

class MyQueue<T> : Queue<T> {
    private val _queue = SingleLinkedList<T>()

    override fun enqueue(element: T) {
        _queue.addLast(element)
    }

    override fun dequeue(): T? = _queue.removeFirst()

    override fun peek(): T? = _queue.first()

    override fun iterator(): Iterator<T> = _queue.iterator()
}