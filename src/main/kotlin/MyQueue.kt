import java.util.*

fun <T> queueOf(vararg elements: T): Queue<T> = elements.asQueue()
fun <T> queueOf(): Queue<T> = MyQueue()

fun <T> Array<out T>.asQueue(): Queue<T> {
    val queue = MyQueue<T>()
    forEach(queue::enqueue)
    return queue
}

class MyQueue<T> : Queue<T> {
    private val _queue: MutableList<T> = LinkedList<T>()

    inner class QueueIterator : Iterator<T> {
        private var cursor: Int = 0

        override fun hasNext(): Boolean = cursor < _queue.size

        override fun next(): T = _queue[cursor++]
    }

    override fun enqueue(element: T) {
        _queue.add(element)
    }

    override fun dequeue(): T? = if (_queue.isEmpty()) null else _queue.removeFirst()

    override fun peek(): T? = if (_queue.isEmpty()) null else _queue.first()

    override fun iterator(): Iterator<T> = QueueIterator()
}