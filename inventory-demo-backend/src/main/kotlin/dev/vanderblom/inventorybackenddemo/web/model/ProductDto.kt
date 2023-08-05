package dev.vanderblom.inventorybackenddemo.web.model

import dev.vanderblom.inventorybackenddemo.service.model.ProductModel
import org.mapstruct.Mapper

data class ProductDto(
    val id: Long,
    val name: String,
    val inventory: Long
)

@Mapper
interface ProductMapper {
    fun toDto(model: ProductModel): ProductDto
    fun toModel(dto: ProductDto): ProductModel
}