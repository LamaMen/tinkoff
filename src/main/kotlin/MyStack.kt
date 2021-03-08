fun <T> stackOf(vararg elements: T): Stack<T> = elements.asStack()
fun <T> stackOf(): Stack<T> = MyStack()

fun <T> Array<out T>.asStack(): Stack<T> {
    val stack = MyStack<T>()
    forEach(stack::push)
    return stack
}

class MyStack<T> : Stack<T> {
    private val _stack = SingleLinkedList<T>()

    override fun push(element: T) {
        _stack.addFirst(element)
    }

    override fun pop(): T? = _stack.removeFirst()

    override fun peek(): T? = _stack.first()

    override fun iterator(): Iterator<T> = _stack.iterator()
}