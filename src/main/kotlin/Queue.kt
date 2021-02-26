interface Queue<T> : Iterable<T> {
    fun enqueue(element: T)
    fun dequeue(): T
    fun peek() : T
}