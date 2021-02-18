import classes.EmailMessage
import classes.Letter
import classes.Message
import classes.SocialMediaMessage

fun List<Message>.print() {
    println("List of messages:")
    this.forEach { println("  ${it.getMessage()}") }
    println("End of list.\n")
}

fun main() {
    val email = EmailMessage("Ilia", "Danny", "Hello!")
    val emailAnswerString = email.answer("Hi, dear friend. How many years have we not seen?!")
    val emailAnswerInt = emailAnswerString.answer(5)
    val letter = Letter("Alex", "Kate", "I love you!")
    val socialMedia = SocialMediaMessage("Kate", "Alex", "Do you still write letters ???")

    val massages = listOf(email, emailAnswerString, emailAnswerInt, letter, socialMedia)

    socialMedia.isRead()
    massages.print()

    letter.printOut()
    socialMedia.isRead()
    massages.print()
}