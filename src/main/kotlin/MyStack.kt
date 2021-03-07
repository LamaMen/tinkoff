import java.util.*

fun <T> stackOf(vararg elements: T): Stack<T> = elements.asStack()
fun <T> stackOf(): Stack<T> = MyStack()

fun <T> Array<out T>.asStack(): Stack<T> {
    val stack = MyStack<T>()
    forEach(stack::push)
    return stack
}

class MyStack<T> : Stack<T> {

    private val _stack = LinkedList<T>()

    inner class StackIterator : Iterator<T> {
        private var cursor = _stack.lastIndex

        override fun hasNext(): Boolean = cursor >= 0

        override fun next(): T = _stack[cursor--]
    }

    override fun push(element: T) {
        _stack.add(element)
    }

    override fun pop(): T? = if (_stack.isEmpty()) null else _stack.removeLast()

    override fun peek(): T? = if (_stack.isEmpty()) null else _stack.last()

    override fun iterator(): Iterator<T> = StackIterator()
}