fun <T> Iterable<T>.print() {
    for (elem in this) {
        print("$elem ")
    }
    println()
}

fun main() {
    println("Queue:")
    val queue = queueOf(1, 2, 3, 4)
    print("  Начальное состояние: ")
    queue.print()

    queue.enqueue(5)
    queue.enqueue(6)
    println("  Добавили 5 и 6")
    println("  Результат dequeue: ${queue.dequeue()}")
    println("  Результат peek: ${queue.peek()}")
    print("  Конечное состояние: ")
    queue.print()

    println("Stack:")
    val stack = stackOf(1, 2, 3, 4)
    print("  Начальное состояние: ")
    stack.print()

    stack.push(5)
    stack.push(6)
    println("  Добавили 5 и 6")
    println("  Результат pop: ${stack.pop()}")
    println("  Результат peek: ${stack.peek()}")
    print("  Конечное состояние: ")
    stack.print()
}