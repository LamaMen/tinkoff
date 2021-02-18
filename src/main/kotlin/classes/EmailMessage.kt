package classes

class EmailMessage(override val sender: String, override val addressee: String, override val body: String) : Message() {
    override val type: String = "email massage"

    fun answer(message: String) = EmailMessage(addressee, sender, message)
    fun answer(message: Int) = answer(message.toString())
}