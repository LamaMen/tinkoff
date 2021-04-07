package models

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Coast(
    title: String,
    amount: Long,
    @Column(name = "date") @Temporal(TemporalType.DATE) val date: Date,
    @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn(name = "user_id") val user: User
) : BaseFinanceEntity(title, amount)
