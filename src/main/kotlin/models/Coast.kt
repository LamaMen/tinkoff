package models

import java.util.*
import javax.persistence.*

@Entity
class Coast(
    title: String,
    amount: Long,
    @Column(name = "date") @Temporal(TemporalType.DATE) val date: Date,
) : BaseFinanceEntity(title, amount) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    var user: User? = null
}
