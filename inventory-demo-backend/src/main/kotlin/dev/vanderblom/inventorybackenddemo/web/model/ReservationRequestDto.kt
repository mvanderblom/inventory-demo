package dev.vanderblom.inventorybackenddemo.web.model

import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.mapstruct.Mapper

data class ReservationRequestDto(
    val amount: Long,
    val seconds: Long
)

@Mapper
interface ReservationRequestMapper {
    fun toDto(model: ReservationRequestModel): ReservationRequestDto
    fun toModel(dto: ReservationRequestDto): ReservationRequestModel
}
