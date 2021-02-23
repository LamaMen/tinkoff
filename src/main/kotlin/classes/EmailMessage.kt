package classes

class EmailMessage(override val sender: String, override val receiver: String, override val body: String) : Message() {
    override val type: String = "email massage"

    fun reply(message: String) = EmailMessage(receiver, sender, message)
    fun reply(message: Int) = reply(message.toString())
}