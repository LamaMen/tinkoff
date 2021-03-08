class SingleLinkedList<T> : Iterable<T> {
    private var first: Node<T>? = null
    private var last: Node<T>? = null

    fun addLast(element: T) {
        val l = last
        val newNode = Node(element, null)
        last = newNode

        if (l == null)
            first = newNode
        else
            l.next = newNode
    }

    fun addFirst(element: T) {
        val f = first
        val newNode = Node(element, f)
        first = newNode

        if (f == null)
            last = newNode
    }

    fun removeFirst(): T? {
        val f = first ?: return null

        val element = f.item
        first = f.next

        f.next = null
        f.item = null

        if (first == null)
            last = null

        return element
    }

    fun first() = first?.item

    private data class Node<E>(var item: E?, var next: Node<E>?)

    inner class SingleLinkedListIterator : Iterator<T> {
        private var cursor = first

        override fun hasNext(): Boolean = cursor != null

        override fun next(): T {
            val current = cursor ?: throw NoSuchElementException()
            val element = current.item ?: throw NoSuchElementException()
            cursor = current.next
            return element
        }
    }

    override fun iterator(): Iterator<T> = SingleLinkedListIterator()
}