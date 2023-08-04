package dev.vanderblom.inventorybackenddemo.data.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Product(
        var name: String,
        var inventory: Long,
        @Id @GeneratedValue var id: Long? = null

) {

}