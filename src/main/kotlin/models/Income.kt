package models

import javax.persistence.*

@Entity
class Income(
    title: String,
    amount: Long,
) : BaseFinanceEntity(title, amount) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    var user: User? = null
}