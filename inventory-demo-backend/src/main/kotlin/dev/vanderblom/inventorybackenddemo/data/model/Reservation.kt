package dev.vanderblom.inventorybackenddemo.data.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Reservation(
    var amount: Long,
    var expires: LocalDateTime,
    @Id @GeneratedValue var id: Long? = null

)
