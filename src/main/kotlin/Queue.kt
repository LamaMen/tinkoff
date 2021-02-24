interface Queue<T> {
    fun enqueue(element: T)
    fun dequeue(): T
}