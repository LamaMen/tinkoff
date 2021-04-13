package models

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Income(
    title: String,
    amount: Long,
) : BaseFinanceEntity(title, amount) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
}