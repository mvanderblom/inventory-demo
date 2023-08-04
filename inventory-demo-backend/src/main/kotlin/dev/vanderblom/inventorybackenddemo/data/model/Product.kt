package dev.vanderblom.inventorybackenddemo.data.model

import jakarta.persistence.*

@Entity
data class Product(
        var name: String,
        var inventory: Long,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val reservations: MutableList<Reservation> = mutableListOf(),
        @Id @GeneratedValue var id: Long? = null

)