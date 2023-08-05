package dev.vanderblom.inventorybackenddemo.web.model

import dev.vanderblom.inventorybackenddemo.service.model.ProductMutationModel
import org.mapstruct.Mapper

data class ProductMutationDto(
    val name: String,
    val inventory: Long
)

@Mapper
interface ProductMutationMapper {
    fun toDto(model: ProductMutationModel): ProductMutationDto
    fun toModel(dto: ProductMutationDto): ProductMutationModel
}