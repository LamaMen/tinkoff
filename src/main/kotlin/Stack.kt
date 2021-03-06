interface Stack<T> : Iterable<T> {
    fun push(element: T)
    fun pop(): T?
    fun peek(): T?
}