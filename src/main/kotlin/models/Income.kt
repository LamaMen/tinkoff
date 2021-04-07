package models

import javax.persistence.*

@Entity
class Income(
    title: String,
    amount: Long,
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn(name = "user_id") val user: User
) : BaseFinanceEntity(title, amount)