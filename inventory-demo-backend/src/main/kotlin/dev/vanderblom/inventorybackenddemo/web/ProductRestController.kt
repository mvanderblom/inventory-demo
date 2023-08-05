package dev.vanderblom.inventorybackenddemo.web

import dev.vanderblom.inventorybackenddemo.service.ProductInventoryService
import dev.vanderblom.inventorybackenddemo.web.model.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/product/")
class ProductRestController(
    private val service: ProductInventoryService,
    private val productMapper: ProductMapper,
    private val productMutationMapper: ProductMutationMapper,
    private val reservationRequestMapper: ReservationRequestMapper
) {

    @Operation(summary = "Returns a list of all products")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved")
        ]
    )
    @GetMapping("/")
    fun list(): List<ProductDto> = service.getAllProducts()
        .map(productMapper::toDto)

    @Operation(
        summary = "Creates a new product",
        description = "Creates a new product returning the newly created product including its id"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully created")
        ]
    )
    @PostMapping("/")
    fun create(@RequestBody productMutation: ProductMutationDto): ProductDto {
        val mutationModel = productMutationMapper.toModel(productMutation)
        val productModel = service.createProduct(mutationModel)
        return productMapper.toDto(productModel)
    }

    @Operation(summary = "Returns a product identified by the provided id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            ApiResponse(responseCode = "404", description = "The product wasn't found"),
        ]
    )
    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = productMapper.toDto(service.readProduct(id))

    @Operation(summary = "Updates a products information")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            ApiResponse(responseCode = "404", description = "The product wasn't found"),
        ]
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody productMutation: ProductMutationDto): ProductDto {
        val mutationModel = productMutationMapper.toModel(productMutation)
        val productModel = service.updateProduct(id, mutationModel)
        return productMapper.toDto(productModel)
    }

    @Operation(
        summary = "Reserves some products for a period of time",
        description = "Allows you to reserve a certain amount of a specific product for a period of time (max 5 minutes)"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            ApiResponse(responseCode = "404", description = "The product wasn't found"),
            ApiResponse(
                responseCode = "400",
                description = "The inventory of the product is lower than the amount you want to reserve"
            ),
            ApiResponse(
                responseCode = "400",
                description = "When you try to reserve a product for longer than 5 minutes"
            ),
        ]
    )
    @PutMapping("/{id}/reserve")
    fun reserve(@PathVariable id: Long, @RequestBody reservationRequest: ReservationRequestDto): ProductDto {
        val reservationRequestModel = reservationRequestMapper.toModel(reservationRequest)
        val productModel = service.createReservation(id, reservationRequestModel)
        return productMapper.toDto(productModel)
    }

    @Operation(summary = "Deletes the product identified by the provided id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully deleted"),
            ApiResponse(responseCode = "404", description = "The product wasn't found"),
        ]
    )
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.deleteProduct(id)

}