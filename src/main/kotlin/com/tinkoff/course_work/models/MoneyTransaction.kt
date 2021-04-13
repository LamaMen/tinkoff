package com.tinkoff.course_work.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "money_transaction")
class MoneyTransaction(
    title: String,
    amount: Long,
    @Column(name = "is_coast") val isCoast: Boolean = true,
    @Column(name = "date") @Temporal(TemporalType.DATE) val date: Date = Date(),
) : BaseFinanceEntity(title, amount) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
}
