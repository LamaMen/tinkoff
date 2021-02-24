fun <T> stackOf(vararg elements: T): Stack<T> = elements.asStack()

fun <T> Array<out T>.asStack(): Stack<T> {
    val stack = MyStack<T>()
    forEach { stack.push(it) }
    return stack
}

class MyStack<T> : Stack<T> {
    private val stack = mutableListOf<T>()

    override fun push(element: T) {
        stack.add(element)
    }

    override fun pop(): T = stack.removeLast()
}