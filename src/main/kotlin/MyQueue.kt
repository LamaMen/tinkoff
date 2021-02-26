fun <T> queueOf(vararg elements: T): Queue<T> = elements.asQueue()

fun <T> Array<out T>.asQueue(): Queue<T> {
    val queue = MyQueue<T>()
    forEach { queue.enqueue(it) }
    return queue
}

class MyQueue<T> : Queue<T> {
    private val _queue = mutableListOf<T>()

    inner class QueueIterator : Iterator<T> {
        private var cursor: Int = 0

        override fun hasNext(): Boolean = cursor < _queue.size

        override fun next(): T = _queue[cursor++]
    }

    override fun enqueue(element: T) {
        _queue.add(element)
    }

    override fun dequeue(): T = _queue.removeFirst()

    override fun peek(): T = _queue.first()

    override fun iterator(): Iterator<T> = QueueIterator()
}