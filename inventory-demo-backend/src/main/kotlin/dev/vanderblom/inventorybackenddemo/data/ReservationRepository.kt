package dev.vanderblom.inventorybackenddemo.data

import dev.vanderblom.inventorybackenddemo.data.model.Reservation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
interface ReservationRepository : CrudRepository<Reservation, Long> {
    fun deleteByExpiresBefore(time: LocalDateTime)
}