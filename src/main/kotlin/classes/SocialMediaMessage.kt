package classes

class SocialMediaMessage(override val sender: String, override val receiver: String, override val body: String) : Message() {
    override val type: String = "social media message"
    private var isRead = false

    fun isRead() {
        if (!isRead) println("User $receiver has not read this message yet.")
        else println("User $receiver has already read this message.")
    }

    override fun getMessage(): String {
        isRead = true
        return super.getMessage()
    }
}