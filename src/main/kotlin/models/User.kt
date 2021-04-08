package models

import javax.persistence.*

@Entity
@Table(name = "user_account")
class User(
    @Column(name = "name") val name: String,
    @Column(name = "login", unique = true) val login: String,
    @Column(name = "password") val password: String,
) : BaseEntity() {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    private val coasts: MutableList<Coast> = ArrayList()
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    private val incomes: MutableList<Income> = ArrayList()

    fun addCoast(coast: Coast) {
        coast.user = this
        coasts.add(coast)
    }
}
