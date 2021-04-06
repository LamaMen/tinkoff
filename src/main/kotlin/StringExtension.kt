fun String.isEvenLength(): Boolean {
    if (this.isEmpty()) throw StringEmptyException("String is empty")

    return this.length % 2 == 0
}

class StringEmptyException(message: String) : Exception(message)