package classes

abstract class Message {
    abstract val body: String
    abstract val sender: String
    abstract val addressee: String
    abstract val type: String

    open fun getMessage(): String = "$sender sent a $type to $addressee. Massage text: '$body'"
}