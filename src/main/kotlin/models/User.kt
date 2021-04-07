package models

import javax.persistence.*

@Entity
@Table(name = "user_account")
class User(
    @Column(name = "name") val name: String,
    @Column(name = "login", unique = true) val login: String,
    @Column(name = "password") val password: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user") val coasts: MutableList<Coast>,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user") val incomes: MutableList<Income>
) : BaseEntity()
