package classes

class Letter(override val sender: String, override val receiver: String, override val body: String) : Message() {
    override val type: String = "letter"
    private var isSealed = true

    fun printOut() {
        isSealed = false
    }

    override fun getMessage() =
            if (isSealed) "This letter is sealed."
            else super.getMessage()
}