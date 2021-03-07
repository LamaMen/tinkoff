import java.util.*
import kotlin.NoSuchElementException

fun <T> queueOf(vararg elements: T): Queue<T> = elements.asQueue()
fun <T> queueOf(): Queue<T> = MyQueue()

fun <T> Array<out T>.asQueue(): Queue<T> {
    val queue = MyQueue<T>()
    forEach(queue::enqueue)
    return queue
}

class MyQueue<T> : Queue<T> {
    private var first: Node<T>? = null
    private var last: Node<T>? = null

    inner class QueueIterator : Iterator<T> {
        private var cursor = first

        override fun hasNext(): Boolean = cursor != null

        override fun next(): T {
            val current = cursor ?: throw NoSuchElementException()
            val element = current.item ?: throw NoSuchElementException()
            cursor = current.next
            return element
        }
    }

    override fun enqueue(element: T) {
        val f = last
        val newNode = Node(element, null)
        last = newNode

        if (f == null)
            first = newNode
        else
            f.next = newNode
    }

    override fun dequeue(): T? {
        val f = first ?: return null

        val element = f.item
        first = f.next

        f.next = null
        f.item = null

        if (first == null)
            last = null

        return element
    }

    override fun peek(): T? = first?.item

    override fun iterator(): Iterator<T> = QueueIterator()

    private data class Node<E>(var item: E?, var next: Node<E>?)
}