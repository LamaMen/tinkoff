package models

import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseFinanceEntity(
    @Column(name = "title")
    val title: String,
    @Column(name = "amount")
    val amount: Long
) : BaseEntity()
