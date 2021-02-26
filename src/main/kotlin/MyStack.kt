fun <T> stackOf(vararg elements: T): Stack<T> = elements.asStack()

fun <T> Array<out T>.asStack(): Stack<T> {
    val stack = MyStack<T>()
    forEach { stack.push(it) }
    return stack
}

class MyStack<T> : Stack<T> {

    private val _stack = mutableListOf<T>()

    inner class StackIterator : Iterator<T> {
        private var cursor = _stack.lastIndex

        override fun hasNext(): Boolean = cursor >= 0

        override fun next(): T = _stack[cursor--]
    }

    override fun push(element: T) {
        _stack.add(element)
    }

    override fun pop(): T = _stack.removeLast()

    override fun peek(): T = _stack.last()

    override fun iterator(): Iterator<T> = StackIterator()
}