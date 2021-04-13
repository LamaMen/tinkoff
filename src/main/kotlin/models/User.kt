package models

import javax.persistence.*

@Entity
@Table(name = "user_account")
class User(
    @Column(name = "name") val name: String,
    @Column(name = "login", unique = true) val login: String,
    @Column(name = "password") val password: String,
) : BaseEntity() {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user", fetch = FetchType.LAZY)
    private val coasts: MutableSet<Coast> = mutableSetOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user", fetch = FetchType.LAZY)
    private val incomes: MutableSet<Income> = mutableSetOf()
}
